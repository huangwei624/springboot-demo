package com.middleyun.java;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @title 加载类路径下的配置文件
 * @description
 * @author huangwei
 * @createDate 2021/1/28
 * @version 1.0
 */
public class LoadPath {

    public static void main(String[] args) throws IOException {


        InputStream inputStream1 = LoadPath.class.getClassLoader().getResourceAsStream("com/middleyun/config/path.properties");

        InputStream inputStream2 = LoadPath.class.getResourceAsStream("/com/middleyun/config/path.properties");

        InputStream inputStream3 = LoadPath.class.getClassLoader().getResourceAsStream("config/path.properties");

        InputStream inputStream4 = LoadPath.class.getResourceAsStream("/config/path.properties");


        Properties properties = new Properties();
        properties.load(inputStream4);
        String path = properties.getProperty("path");
        System.out.println(path);
    }

}
