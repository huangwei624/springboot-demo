package com.middleyun.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.net.URL;

public class HoconUtils {
    /**
     * 配置文件对象
     */
    private static Config config;

    private HoconUtils() {
    }

    /**
     * 通过配置文件的本地路径加载配置
     * @param configPath    配置文件地址
     * @return
     */
    private static Boolean loadConfig(String configPath) {
        if ("".equals(configPath) || configPath == null) {
            return false;
        }
        if (!configPath.endsWith(".conf")) {
            return false;
        }
        File file = new File(configPath);
        config = ConfigFactory.parseFile(file);
        return config != null;
    }

    /**
     * 通过配置文件url 加载配置
     * @param configUri 配置文件远程url
     * @return
     */
    private static Boolean loadConfig(URL configUri) {
        if (configUri == null) {
            return false;
        }
        config = ConfigFactory.parseURL(configUri);
        return config != null;
    }

    /**
     *  获取配置文件对象
     */
    public static Config getConfig(String configPath) {
        if (loadConfig(configPath)) {
            return config;
        }
        return null;
    }

    /**
     *  获取配置文件对象
     */
    public static Config getConfig(URL configUrl) {
        if (loadConfig(configUrl)) {
            return config;
        }
        return null;
    }
}
