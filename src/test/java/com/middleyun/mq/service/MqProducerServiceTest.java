package com.middleyun.mq.service;

import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MqMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class MqProducerServiceTest {

    @Autowired
    private MqProducerService mqProducerService;

    @Test
    void sendSmsMessage() {
        String messageId = UUID.randomUUID().toString();
        MqMessage mqMessage = MqMessage.builder().id(messageId)
                .createTime(LocalDateTime.now())
                .data("sms:this is a simply message").build();

        mqProducerService.sendSmsMessage(mqMessage, MqConstant.DIRECT_SMS_EXCHANGE, "sms");
    }

    @Test
    void sendEmailMessage() {
    }
}