package com.middleyun.mq.service;

import com.middleyun.mq.domain.MqMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Service
public class MqProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送短信消息
     */
    public void sendSmsMessage(MqMessage mqMessage, String exchangeName, String routingKey) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, mqMessage);
    }

    /**
     * 发送短信消息
     */
    public Boolean sendEmailMessage(MqMessage mqMessage, String exchangeName, String routingKey) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, mqMessage);

        return true;
    }

}
