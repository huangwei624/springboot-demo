package com.middleyun.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.middleyun.common.util.redis.RedisOpsUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
import com.middleyun.mq.domain.MessageEntity;
import com.middleyun.mq.util.MessageUtils;
import com.rabbitmq.client.Channel;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Component
public class MqSmsConsumerListener {

    @Autowired
    private RedissonClient redissonClient;

    @RabbitListener(queues = {MqConstant.DIRECT_SMS_QUEUE1})
    public void handle(Message message, Channel channel) throws IOException {
        MessageBody messageBody = null;
        String messageKey = "";
        MessageEntity messageEntity = null;
        Integer successStatus = MqConstant.MessageStatus.CONSUME_SUCCESS.getStatus();
        RLock lock = redissonClient.getLock("mqlock");
        lock.lock(30, TimeUnit.SECONDS);
        System.out.println("consumer:加锁成功");
        try {
            messageBody = JSONObject.parseObject(message.getBody(), MessageBody.class);
            messageKey = MessageUtils.getMessageRedisKey(messageBody.getId());
            messageEntity = (MessageEntity) RedisOpsUtils.get(messageKey);
            if (messageEntity == null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            // 如果消息重试次数达到上限, 保存消息到数据库，人工处理 todo
            if (messageEntity.getRetryCount() > MqConstant.MAX_RETRY_COUNT) {
                System.out.println("消息重试次数达到上线");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            // 如果该消息已经被消费过，直接返回，防止重复消费
            if (messageEntity.getStatus().equals(successStatus)) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("消息重复：" + messageBody.getId());
                return;
            }
            // 如果该消息的状态为消费失败，说名该消息在重试消费
            if (messageEntity.getStatus().equals(MqConstant.MessageStatus.CONSUME_FAIL.getStatus())) {
                messageEntity.setRetryCount(messageEntity.getRetryCount() + 1);
            }
            // 模拟消费异常
            int a = 4 / (new Random().nextInt(10) % 2);
            System.out.println("成功消费：" + messageBody.getId());
            // 设置消息状态为消费成功
            messageEntity.setStatus(MqConstant.MessageStatus.CONSUME_SUCCESS.getStatus());
            RedisOpsUtils.set(MessageUtils.getMessageRedisKey(messageBody.getId()), messageEntity);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (messageBody != null) {
                System.out.println("消息消费失败：" + messageBody.getId());
            }

            // 投递失败，设置消息投递状态为消费失败, 每次消费消息时需要根据消息的重试次数判断是否再次消费消息（最大重试次数）
            // 设置消息状态为消费失败
            if (messageEntity != null && !messageEntity.getStatus().equals(successStatus)) {
                messageEntity.setStatus(MqConstant.MessageStatus.CONSUME_FAIL.getStatus());
                RedisOpsUtils.set(MessageUtils.getMessageRedisKey(messageBody.getId()), messageEntity);
            }
            /*
             * 第一个参数：消息投递标识
             * 第二个参数：是否批量投递，将小于当前的 multiple 的所有消息进行投递，没投递一个消息multiple都会进行加1
             * 第三个参数：是否重新放入队列，建议设置为false, 否则会陷入无限循环投递
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }finally {
            System.out.println("consumer:释放锁");
            lock.unlock();
        }
    }

}
