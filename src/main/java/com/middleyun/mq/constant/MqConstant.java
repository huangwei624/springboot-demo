package com.middleyun.mq.constant;

import lombok.Data;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
public class MqConstant {

    /**
     * 直连交换机相关的
     */
    public static final String DIRECT_SMS_QUEUE1 = "direct_sms_queue1";
    public static final String DIRECT_SMS_QUEUE2 = "direct_sms_queue2";
    public static final String DIRECT_EMAIL_QUEUE = "direct_email_queue";
    public static final String DIRECT_SMS_EXCHANGE = "direct_sms_exchange";
    public static final String DIRECT_EMAIL_EXCHANGE = "direct_email_exchange";

    /**
     * 消息最大重试次数
     */
    public static final Integer MAX_RETRY_COUNT = 3;

    /**
     * 消息状态枚举
     */
    public enum MessageStatus {
        SENDING(0, "投递中"),
        SEND_SUCCESS(1, "投递成功"),
        SEND_FAIL(2, "投递失败"),
        FROM_EXCHANGE_TO_QUEUE_FAIL(3, "消息未由交换机转发到队列中"),
        CONSUME_SUCCESS(4, "消费成功"),
        CONSUME_FAIL(5, "消费失败"),
        ;

        private Integer status;
        private String description;

        MessageStatus(Integer status, String description) {
            this.status = status;
            this.description = description;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
