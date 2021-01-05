package com.middleyun.io;

import com.middleyun.mq.domain.MqMessage;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/5
 * @version 1.0
 */
public class ObjectOutputStreamTest {

    @Test
    public void convert() throws IOException, ClassNotFoundException {
        MqMessage message = MqMessage.builder().createTime(LocalDateTime.now()).id(UUID.randomUUID().toString())
                .data("message").build();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        System.out.println(object);
    }

}
