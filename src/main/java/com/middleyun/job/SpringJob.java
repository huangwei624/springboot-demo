package com.middleyun.job;

import com.middleyun.common.util.redis.RedisOpsUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
import com.middleyun.mq.domain.MessageEntity;
import com.middleyun.mq.util.MessageUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @title Spring定时任务
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
@Component
@EnableScheduling
public class SpringJob {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时获取消费失败的消息然后重发，每隔20s 执行一次
     */
    @Scheduled(cron = "*/20 * * * * *")
    public void retryFailMqMessage() {
        // 这种做法是不合理的，对于大量消息的话，不可取
        List<Object> messageEntities = RedisOpsUtils.prefixGet(MessageUtils.MQ_PREFIX);
        if (messageEntities == null || messageEntities.size() == 0) {
            return;
        }
        messageEntities.forEach(messageEntity -> {
            MessageEntity message = (MessageEntity) messageEntity;
            // 如果已经消费成功或者消息重试次数达到上限则忽略
            if (message.getStatus().equals(MqConstant.MessageStatus.CONSUME_SUCCESS.getStatus())
                    || message.getRetryCount() > MqConstant.MAX_RETRY_COUNT) {
                return;
            }
            MessageBody messageBody = message.getMessageBody();
            CorrelationData correlationData = new CorrelationData(messageBody.getId());
            rabbitTemplate.convertAndSend(MqConstant.DIRECT_SMS_EXCHANGE, "sms", messageBody, correlationData);
        });
    }

    /**
     * 定时删除消费成功的消息
     */
    @Scheduled(cron = "*/20 * * * * *")
    public void deleteConsumerSuccessMessage() {
        List<Object> messageEntities = RedisOpsUtils.prefixGet(MessageUtils.MQ_PREFIX);
        if (messageEntities == null || messageEntities.size() == 0) {
            return;
        }
        messageEntities.forEach(messageEntity -> {
            MessageEntity message = (MessageEntity) messageEntity;
            // 如果已经消费成功或者消息重试次数达到上限则忽略
            if (message.getStatus().equals(MqConstant.MessageStatus.CONSUME_SUCCESS.getStatus())) {
                RedisOpsUtils.del(MessageUtils.getMessageRedisKey(message.getMessgeId()));
            }
        });
    }

}
