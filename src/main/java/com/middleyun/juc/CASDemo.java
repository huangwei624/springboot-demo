package com.middleyun.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 学习乐观锁的两种实现方式：
 * 1， 一般CAS 的使用方式，会有ABA 问题
 * 2， 基于版本号的CAS, 可以解决ABA 问题
 */
public class CASDemo {

    public static void main(String[] args) {
//        commonCas();
        resolveABACas();
    }

    /**
     * 一般 cas
     */
    private static void commonCas() {
        AtomicInteger atomicInteger = new AtomicInteger(1024);
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "====>" + atomicInteger.compareAndSet(1024, 1025));

            System.out.println(Thread.currentThread().getName() +"====>" + atomicInteger.compareAndSet(1025, 1024));
        }, "T1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // T2 期望 数字的变化过程是： 1 -> 3, 可是实际情况可能是 1 --> 2 --> 1 --->3, 这里的代码只是模拟，逻辑并不对
            System.out.println(Thread.currentThread().getName() + "====>" + atomicInteger.compareAndSet(1024, 1026));
        }, "T2").start();
    }

    /**
     * 解决ABA 问题
     */
    private static void resolveABACas() {
        AtomicStampedReference<Integer> atomicInteger = new AtomicStampedReference<Integer>(1, 1);
        new Thread(()->{
            int stamp = atomicInteger.getStamp();
            System.out.println(Thread.currentThread().getName() + "====>" + atomicInteger.compareAndSet(1, 2, stamp, stamp + 1));
            stamp = atomicInteger.getStamp();
            System.out.println(Thread.currentThread().getName() +"====>" + atomicInteger.compareAndSet(2, 1, stamp, stamp+1));
        }, "T1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // T2 期望 数字的变化过程是： 1 -> 3, 可是实际情况可能是 1 --> 2 --> 1 --->3, 这里的代码只是模拟，逻辑并不对
            System.out.println(Thread.currentThread().getName() + "====>" + atomicInteger.compareAndSet(1, 3, 1, 2));
        }, "T2").start();


    }

}
