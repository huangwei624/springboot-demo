package com.middleyun.reflex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Student {

    public void method() {
        int i = 2 / 0;
        System.out.println(i);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Student student = new Student();
        Class<? extends Student> aClass = student.getClass();
        Method method = aClass.getDeclaredMethod("method");
        try {
            method.invoke(student);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (e instanceof InvocationTargetException) {
                InvocationTargetException e1 = (InvocationTargetException) e;
                e1.getTargetException().printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
    }


}
