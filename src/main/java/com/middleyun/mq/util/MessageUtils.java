package com.middleyun.mq.util;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
public class MessageUtils {

    /**
     * mq消息实体在redis中的key前缀， mq:**
     */
    public static final String MQ_PREFIX = "mq:";

    /**
     * 获取消息实体在redis 中得key
     * @param messageId
     * @return
     */
    public static String getMessageRedisKey(String messageId) {
        return MQ_PREFIX + messageId;
    }

}
