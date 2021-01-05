package com.middleyun.mq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
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
        }
    }


    /**
     * 消息由交换机未能转发到队列回调逻辑处理
     */
    public static class ReturnCallbackImpl implements RabbitTemplate.ReturnCallback {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("消息未能由交换机转发到队列");
        }
    }



}
