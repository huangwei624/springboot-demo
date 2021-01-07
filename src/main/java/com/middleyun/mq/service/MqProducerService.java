package com.middleyun.mq.service;

import com.middleyun.common.util.redis.RedisOpsUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
import com.middleyun.mq.domain.MessageEntity;
import com.middleyun.mq.util.MessageUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void sendSmsMessage(MessageBody messageBody, String exchangeName, String routingKey) {
        CorrelationData correlationData = new CorrelationData(messageBody.getId());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageBody, correlationData);
        // 创建消息实体
        MessageEntity messageEntity = MessageEntity.builder().id(1L)
                .messgeId(messageBody.getId())
                .status(MqConstant.MessageStatus.SENDING.getStatus())
                .retryCount(0)
                .messageBody(messageBody).build();
        RedisOpsUtils.set(MessageUtils.getMessageRedisKey(messageBody.getId()), messageEntity);
    }

    /**
     * 发送短信消息
     */
    public Boolean sendEmailMessage(MessageBody messageBody, String exchangeName, String routingKey) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageBody);

        return true;
    }

}
