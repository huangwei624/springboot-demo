package com.middleyun.mq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.middleyun.common.util.redis.RedisOpsUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
import com.middleyun.mq.domain.MessageEntity;
import com.middleyun.mq.util.MessageUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 参考文档
 * https://zhuanlan.zhihu.com/p/152325703
 * https://blog.csdn.net/wxb880114/article/details/105836274
 *
 * @title rabbitmq 配置类
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Import({DirectRabbitMQConfiguration.class, FanoutRabbitMQConfiguration.class, TopicRabbitMQConfiguration.class})
@Configuration
public class RabbitMQConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmCallbackImpl confirmCallback;

    @Autowired
    private ReturnCallbackImpl returnCallback;



    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMessageConverter(new MyMessageConverter<>(MessageBody.class));
    }

    /**
     * 消息是否发送到交换机的回调处理逻辑
     */
    @Configuration
    public static class ConfirmCallbackImpl implements RabbitTemplate.ConfirmCallback {

        @Autowired
        private RedissonClient redissonClient;

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            /*
             * 根据CorrelationData 获取消息id, 从数据库或者redis 中获取消息，
             * 修改消息状态为投递成功（投递失败（client-exchange），通过ack 判断）
             */
            if (correlationData == null) {
                return;
            }
            String messageId = correlationData.getId();
            RLock lock = redissonClient.getLock("mqlock");
            lock.lock(30, TimeUnit.SECONDS);
            System.out.println("confirm:加锁成功");
            try {
                MessageEntity messageEntity = (MessageEntity) RedisOpsUtils.get(MessageUtils.getMessageRedisKey(messageId));
                if (messageEntity == null ||
                        messageEntity.getStatus().equals(MqConstant.MessageStatus.CONSUME_SUCCESS.getStatus()) ||
                        messageEntity.getStatus().equals(MqConstant.MessageStatus.CONSUME_FAIL.getStatus())) {
                    return;
                }
                if (ack) {
                    messageEntity.setStatus(MqConstant.MessageStatus.SEND_SUCCESS.getStatus());
                } else {
                    messageEntity.setStatus(MqConstant.MessageStatus.SEND_FAIL.getStatus());
                }
                RedisOpsUtils.set(MessageUtils.getMessageRedisKey(messageId), messageEntity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("confirm:释放锁");
                lock.unlock();
            }
        }
    }

    /**
     * 消息由交换机未能转发到队列回调逻辑处理
     */
    @Configuration
    public static class ReturnCallbackImpl implements RabbitTemplate.ReturnCallback {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("消息未能由交换机转发到队列");
            MessageBody messageBody = JSONObject.parseObject(message.getBody(), MessageBody.class);

            // 从数据库或者redis 中获取消息，修改消息状态为投递失败(exchange-queue)
            String messageId = messageBody.getId();
            MessageEntity messageEntity = (MessageEntity) RedisOpsUtils.get(MessageUtils.getMessageRedisKey(messageId));
            if (messageEntity != null) {
                messageEntity.setStatus(MqConstant.MessageStatus.FROM_EXCHANGE_TO_QUEUE_FAIL.getStatus());
                RedisOpsUtils.set(MessageUtils.getMessageRedisKey(messageId), messageEntity);
            }
        }
    }

    /**
     * 消息转换器
     * @param <T>
     */
    @SuppressWarnings("all")
    public static class MyMessageConverter<T> extends SimpleMessageConverter {

        private Class<T> clazz;

        public MyMessageConverter(Class<T> clazz) {
            this.clazz = clazz;
        }

        /**
         * Converts from a AMQP Message to an Object.
         * @param message
         */
        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            if (message == null) {
                return null;
            }
            return JSONObject.parseObject(message.getBody(), clazz);
        }

        /**
         * Creates an AMQP Message from the provided Object.
         * @param object
         * @param messageProperties
         */
        @Override
        protected Message createMessage(Object object, MessageProperties messageProperties)
                throws MessageConversionException {
            if (object == null) {
                return null;
            }
            return new Message(JSON.toJSONBytes(object), messageProperties);
        }
    }

}
