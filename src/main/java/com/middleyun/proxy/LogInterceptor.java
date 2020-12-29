package com.middleyun.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class LogInterceptor implements InvocationHandler {

    // 代理对象
    private Object object;

    public LogInterceptor(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(object, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("-----after: " + new Date());
    }

    private void before() {
        System.out.println("-----before: " + new Date());
    }


}
