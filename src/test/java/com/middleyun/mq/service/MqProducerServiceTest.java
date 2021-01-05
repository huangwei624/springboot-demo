package com.middleyun.mq.service;

import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MqMessage;
import org.junit.jupiter.api.Test;
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
        MqMessage mqMessage = MqMessage.builder().id(UUID.randomUUID().toString())
                .createTime(LocalDateTime.now())
                .data("sms:this is a simply message").build();
        mqProducerService.sendSmsMessage(mqMessage, MqConstant.DIRECT_SMS_EXCHANGE, "smsa");
    }

    @Test
    void sendEmailMessage() {
    }
}