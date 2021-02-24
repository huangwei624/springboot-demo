package com.middleyun;

import com.middleyun.test.GetConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        /**
         * 这个返回的是上下文对象，也可以理解为spring容器，容器中存放的都是Bean
         */
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        /**
         * 通过spring上下文获取GetConfig 对象
         */
        GetConfig getConfig = context.getBean(GetConfig.class);
        System.out.println(getConfig.getPort());
    }
}
