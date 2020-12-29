package com.middleyun.proxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        ClassLoader classLoader = userService.getClass().getClassLoader();
        Class<?>[] interfaces = userService.getClass().getInterfaces();

        LogInterceptor logInterceptor = new LogInterceptor(userService);
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(classLoader, interfaces, logInterceptor);
        userServiceProxy.getUserById();
        userServiceProxy.insertUser();
    }
}
