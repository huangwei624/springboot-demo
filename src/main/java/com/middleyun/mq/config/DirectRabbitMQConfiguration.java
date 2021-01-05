package com.middleyun.mq.config;

import com.middleyun.mq.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title 直连交换机消息传递配置
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Configuration
public class DirectRabbitMQConfiguration {

    /**
     * 绑定直连交换机的短信服务队列1
     * @return
     */
    @Bean
    public Queue directSMSQueue1() {
        // 队列默认支持持久化
        return new Queue(MqConstant.DIRECT_SMS_QUEUE1);
    }

    /**
     * 绑定直连交换机的短信服务队列2
     * @return
     */
    @Bean
    public Queue directSMSQueue2() {
        // 队列默认支持持久化
        return new Queue(MqConstant.DIRECT_SMS_QUEUE2);
    }

    /**
     * 绑定直连交换机的邮件队列
     * @return
     */
    @Bean
    public Queue directEmailQueue() {
        // 队列默认支持持久化
        return new Queue(MqConstant.DIRECT_EMAIL_QUEUE);
    }

    /**
     * 直连短信服务交换机
     * @return
     */
    @Bean
    public DirectExchange directSmsExchange() {
        return new DirectExchange(MqConstant.DIRECT_SMS_EXCHANGE);
    }

    /**
     * 直连邮件服务服务交换机
     * @return
     */
    @Bean
    public DirectExchange directEmailExchange() {
        return new DirectExchange(MqConstant.DIRECT_EMAIL_EXCHANGE);
    }

    /**
     * 绑定短信队列1到短信交换机
     * @param directSMSQueue1
     * @param directSmsExchange
     * @return
     */
    @Bean
    public Binding bindSmsQueue1ToSmsExchange(Queue directSMSQueue1, DirectExchange directSmsExchange) {
        return BindingBuilder.bind(directSMSQueue1).to(directSmsExchange).with("sms");
    }

    /**
     * 绑定短信队列1到短信交换机
     * @param directSmsExchange
     * @return
     */
    @Bean
    public Binding bindSmsQueue2ToSmsExchange(Queue directSMSQueue2, DirectExchange directSmsExchange) {
        return BindingBuilder.bind(directSMSQueue2).to(directSmsExchange).with("sms");
    }

    /**
     * 绑定邮件队列到邮件交换机
     * @return
     */
    @Bean
    public Binding bindEmailQueueToEmailExchange(Queue directEmailQueue, DirectExchange directEmailExchange) {
        return BindingBuilder.bind(directEmailQueue).to(directEmailExchange).with("email");
    }


}
