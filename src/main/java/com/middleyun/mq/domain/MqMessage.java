package com.middleyun.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @title mq 消息封装
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqMessage  implements Serializable {
    private String id;
    private LocalDateTime createTime;
    private Object data;
}
