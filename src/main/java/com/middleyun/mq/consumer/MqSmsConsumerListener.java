package com.middleyun.mq.consumer;

import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MqMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Component
@RabbitListener(queues = {MqConstant.DIRECT_SMS_QUEUE1, MqConstant.DIRECT_SMS_QUEUE2})
public class MqSmsConsumerListener {

    @RabbitHandler
    public void handle(MqMessage mqMessage) {
        System.out.println(mqMessage);
    }

}
