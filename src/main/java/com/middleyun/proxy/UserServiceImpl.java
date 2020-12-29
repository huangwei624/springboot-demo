package com.middleyun.proxy;

public class UserServiceImpl implements UserService {
    @Override
    public void getUserById() {
        System.out.println("getUserById...");
    }

    @Override
    public void insertUser() {
        getUserById();
        System.out.println("insertUser...");
    }
}
