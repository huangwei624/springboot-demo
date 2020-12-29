package com.middleyun.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfiguration {

    /**
     * ElasticSearch 高级客服端工具bean
     * @param elasticSearchProperties
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticSearchProperties elasticSearchProperties) {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticSearchProperties.getHost(),
                        elasticSearchProperties.getPort(), elasticSearchProperties.getScheme())));
    }
}
