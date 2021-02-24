package com.middleyun.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/28
 * @version 1.0
 */

/**
 * 加入@Component 会在容器中创建一个GetConfig 对象
 */
@Component
public class GetConfig {

    @Value("${server.port}")
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
