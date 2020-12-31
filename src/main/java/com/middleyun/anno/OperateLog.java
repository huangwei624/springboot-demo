package com.middleyun.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @title 操作日志注解
 * @description
 * @author huang
 * @createDate 2020/12/29
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface OperateLog {
    /**
     * 日志模块名
     * @return
     */
    String moduleName();
}
