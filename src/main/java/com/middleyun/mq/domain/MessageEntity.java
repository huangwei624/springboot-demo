package com.middleyun.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity implements Serializable {

    private Long id;

    /**
     * 消息投递状态，0 投递中， 1 投递成功， 2 投递失败， 3 消息未由交换机转发到队列中， 4 消费成功， 5 消费失败
     */
    private Integer status;

    /**
     * 消息重试次数
     */
    private Integer retryCount;

    /**
     * 消息id
     */
    private String messgeId;

    /**
     * 消息体
     */
    private MessageBody messageBody;

}
