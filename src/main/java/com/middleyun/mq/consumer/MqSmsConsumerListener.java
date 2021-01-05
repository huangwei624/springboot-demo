package com.middleyun.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MqMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Component
public class MqSmsConsumerListener {

    @RabbitListener(queues = {MqConstant.DIRECT_SMS_QUEUE1, MqConstant.DIRECT_SMS_QUEUE2})
    public void handle(Message message, Channel channel)
            throws IOException {
        MqMessage mqMessage = JSONObject.parseObject(message.getBody(), MqMessage.class);
        System.out.println("接受的消息：");
        System.out.println(mqMessage);
        try {
            int a = 4 / (new Random().nextInt(10) % 2);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 投递成功，设置消息状态为消费失败
        } catch (RuntimeException e) {
            e.printStackTrace();
            /*
             * 第一个参数：消息投递标识
             * 第二个参数：是否批量投递，将小于当前的 multiple 的所有消息进行投递，没投递一个消息multiple都会进行加1
             * 第三个参数：是否重新放入队列，建议设置为false, 否则会陷入无限循环投递
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            // todo
            // 投递失败，设置消息投递状态为消费失败, 每次消费消息时需要根据消息的重试次数判断是否再次消费消息（最大重试次数）
        }
    }

}
