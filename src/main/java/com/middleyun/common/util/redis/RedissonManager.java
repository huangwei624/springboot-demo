package com.middleyun.common.util.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * @title Redission 分布式锁
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedissonManager {
    private String redisHost;
    private Integer redisPort;
    private Long connectTimeout = 3000L;
    private Integer readTimeout = 3000;
    private Integer connectionPoolSize = 64;
    private static RedissonClient redissonClient;

    private static Config config = new Config();

    public RedissonClient getRedisson(){
        if (this.redisHost == null || redisPort == null) {
            throw new RuntimeException("redis host or port is null");
        }
        if (redissonClient == null) {
            synchronized (RedissonManager.class) {
                if (redissonClient == null) {
                    SingleServerConfig singleConfig = config.useSingleServer();
                    singleConfig.setAddress("127.0.0.1"  + ":6379");
                    singleConfig.setConnectTimeout(this.connectionPoolSize);
                    singleConfig.setTimeout(this.readTimeout);
                    singleConfig.setConnectionPoolSize(this.connectionPoolSize);
                    return Redisson.create(config);
                }
            }
        }
        return redissonClient;
    }

}
