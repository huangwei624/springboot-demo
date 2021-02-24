package com.middleyun.thread;

import java.util.concurrent.TimeUnit;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/2/19
 * @version 1.0
 */
public class ThreadTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("111");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("222");
                try {
                    t1.join();
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

}
