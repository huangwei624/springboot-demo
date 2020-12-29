package com.middleyun.juc;

import java.util.concurrent.TimeUnit;

/**
 * java 内存模型
 */
public class JMMDemo {
    private static volatile Integer num = 0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (num == 0) {

            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        num ++;

        System.out.println(num);
    }
}
