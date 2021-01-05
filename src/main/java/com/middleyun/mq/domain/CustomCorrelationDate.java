package com.middleyun.mq.domain;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @title 关联消息数据
 * @description
 * @author huangwei
 * @createDate 2021/1/5
 * @version 1.0
 */
public class CustomCorrelationDate extends CorrelationData {

    private MqMessage mqMessage;

    public CustomCorrelationDate(MqMessage mqMessage) {
        super(mqMessage.getId());
        this.mqMessage = mqMessage;
    }

    public MqMessage getMqMessage() {
        return mqMessage;
    }

    public void setMqMessage(MqMessage mqMessage) {
        this.mqMessage = mqMessage;
    }
}
