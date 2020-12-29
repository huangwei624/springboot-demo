package com.middleyun.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {
    private String host;
    // 协议，客服端连接es 服务使用的协议
    private String scheme;
    private Integer port;
}
