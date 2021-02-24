package com.middleyun.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/2/23
 * @version 1.0
 */
public class Condition2Demo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition printStr = lock.newCondition();
        Condition printNum = lock.newCondition();


        new Thread(()->{
            lock.lock();
            try {
                System.out.println("字符打印线程获取到了锁");
                TimeUnit.SECONDS.sleep(2);
                printStr.await();
                for (int i = 0; i < 10; i++) {
                    System.out.println("abcdefg");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        new Thread(()->{
            lock.lock();
            try {
                System.out.println("数字打印线程获取到了锁");
                TimeUnit.SECONDS.sleep(2);
                printStr.signal();
                TimeUnit.SECONDS.sleep(10);
                for (int i = 0; i < 10; i++) {
                    System.out.println("123456789");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }

}
