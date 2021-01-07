package com.middleyun.reflex;

import org.junit.Test;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
public class LoadClassTest {
    @Test
    public void test1() {
        LoadClass loadClass = new LoadClass();
        LoadClass.A a1 = loadClass.new A();
    }

    @Test
    public void test2() {
        String a = LoadClass.a;
    }

    @Test
    public void test3() {
        LoadClass.getA();
    }

    @Test
    public void test4() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("com.middleyun.reflex.LoadClass");
        Class<LoadClass> loadClassClass = LoadClass.class;
        LoadClass loadClass = loadClassClass.newInstance();
    }

}
