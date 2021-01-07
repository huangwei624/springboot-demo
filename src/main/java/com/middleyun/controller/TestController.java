package com.middleyun.controller;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.middleyun.common.util.DateTimeUtils;
import com.middleyun.mq.constant.MqConstant;
import com.middleyun.mq.domain.MessageBody;
import com.middleyun.mq.service.MqProducerService;
import io.swagger.annotations.Api;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Api(tags = "测试接口")
@RestController
public class TestController {

    @GetMapping("/dynamicImg")
    public String downDynamicImg(HttpServletResponse response) throws IOException {
        File file = new File("H:\\picture\\3.jpg");
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        // 下载文件能正常显示中文
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[1024];
        while (-1 != bufferedInputStream.read(bytes, 0, bytes.length)) {
            outputStream.write(bytes, 0, bytes.length);
        }
        outputStream.flush();
        outputStream.close();
        bufferedInputStream.close();
        return "down success";
    }


    @GetMapping("/down")
    public String down() throws HttpProcessException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("H:\\picture\\1_copy.jpg"));
        HttpClientUtil.down(HttpConfig.custom().url("http://localhost:8080/dynamicImg").out(fileOutputStream));
        fileOutputStream.close();
        return "request success";
    }


    @GetMapping("/html")
    public String html() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>test title</title>\n" +
                "</head>\n" +
                "<body style='color: red; width: 100%; text-align: center;'>\n" +
                "\tthis is a test title!\n" +
                "<body>\n" +
                "</html>";
    }

    @Autowired
    private MqProducerService mqProducerService;

    @GetMapping("/redis_lock")
    public String redisLock() {
        String messageId = UUID.randomUUID().toString();
        MessageBody messageBody = MessageBody.builder().id(messageId)
                .createTime(DateTimeUtils.formatDateTime(LocalDateTime.now()))
                .data("sms:this is a simply message").build();

        mqProducerService.sendSmsMessage(messageBody, MqConstant.DIRECT_SMS_EXCHANGE, "sms");
        return "success";
    }

}
