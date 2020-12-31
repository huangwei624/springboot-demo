package com.middleyun.anno;

import java.lang.annotation.*;

/**
 * @title 方法的日志操作注解
 * @description 该注解配合 @OperateLog 使用，@OperateLog注解在类上，该注解注释在方法上
 * @author huangwei
 * @createDate 2020/12/29
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MethodOperateLog {

    /**
     * 该方法的作用描述
     * @return
     */
    String description();
}
