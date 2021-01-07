package com.middleyun.reflex;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
public class LoadClass {
    public static String a = "a";

    {
        System.out.println("普通代码块");
    }

    static {
        System.out.println("静态代码块");
    }

    public static String getA() {
        return a;
    }

    public class A {
        {
            System.out.println("A 的普通代码块");
        }

        public String getA() {
            return a;
        }
    }
}
