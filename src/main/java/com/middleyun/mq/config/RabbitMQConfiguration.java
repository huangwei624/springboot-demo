package com.middleyun.mq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.middleyun.mq.domain.CustomCorrelationDate;
import com.middleyun.mq.domain.MqMessage;
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

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(new ConfirmCallbackImpl());
        rabbitTemplate.setReturnCallback(new ReturnCallbackImpl());
        rabbitTemplate.setMessageConverter(new MyMessageConverter<>(MqMessage.class));
    }

    /**
     * 消息是否发送到交换机的回调处理逻辑
     */
    public static class ConfirmCallbackImpl implements RabbitTemplate.ConfirmCallback {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (ack) {
                System.out.println("消息成功发送到交换机");
            } else {
                System.out.println("消息未发送到交换机：" + cause);
            }
            if (correlationData instanceof CustomCorrelationDate) {
                CustomCorrelationDate customCorrelationDate = (CustomCorrelationDate) correlationData;
                System.out.println(customCorrelationDate.getMqMessage());
            }
            // todo
            // 根据CorrelationData 获取消息id, 从数据库或者redis 中获取消息，修改消息状态为投递成功（投递失败（client-exchange），通过ack 判断）
        }
    }

    /**
     * 消息由交换机未能转发到队列回调逻辑处理
     */
    public static class ReturnCallbackImpl implements RabbitTemplate.ReturnCallback {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("消息未能由交换机转发到队列");
            MqMessage mqMessage = JSONObject.parseObject(message.getBody(), MqMessage.class);
            System.out.println(mqMessage);
            // todo
            // 从数据库或者redis 中获取消息，修改消息状态为投递失败(exchange-queue)
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
