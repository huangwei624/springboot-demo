package com.middleyun.mq.domain;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/4
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqMessage implements Serializable {
    private String id;
    private LocalDateTime createTime;
    private Object data;
}
