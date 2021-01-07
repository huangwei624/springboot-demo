package com.middleyun.mq.service;

import com.middleyun.common.util.DateTimeUtils;
import com.middleyun.common.util.redis.RedisOpsUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
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
        String messageId = UUID.randomUUID().toString();
        MessageBody messageBody = MessageBody.builder().id(messageId)
                .createTime(DateTimeUtils.formatDateTime(LocalDateTime.now()))
                .data("sms:this is a simply message").build();

        mqProducerService.sendSmsMessage(messageBody, MqConstant.DIRECT_SMS_EXCHANGE, "sms");
    }

    @Test
    void sendEmailMessage() {
        RedisOpsUtils redisOpsUtils = new RedisOpsUtils();
    }
}