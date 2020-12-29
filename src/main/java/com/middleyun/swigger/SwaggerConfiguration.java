package com.middleyun.swigger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi(){
        // 响应码信息
        List<ResponseMessage> responseMessage = getResponseMessage();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessage)
                .globalResponseMessage(RequestMethod.POST, responseMessage)
                .globalResponseMessage(RequestMethod.DELETE, responseMessage)
                .globalResponseMessage(RequestMethod.PUT, responseMessage)
                // .host("http://www.baidu.com")
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                // .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("springboot-test-demo")
                .description("接口文档API测试.")
                // .license() // 许可信息
                .contact(new Contact("huang", "http://www.baidu.com", "19674@qq.com"))
                .version("1.0")
                .build();
    }

    /**
     * 全局响应码
     * @return
     */
    private List<ResponseMessage> getResponseMessage() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
//        ResponseMessageBuilder builder = new ResponseMessageBuilder();
//        responseMessages.add(builder.code(500).message("失败").build());
//        responseMessages.add(builder.code(200).message("成功").build());
//        responseMessages.add(builder.code(501).message("未认证").build());
        return responseMessages;
    }

}
