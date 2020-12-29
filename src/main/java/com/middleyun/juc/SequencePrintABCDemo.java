package com.middleyun.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 小测试 顺序打印A B C
 */
public class SequencePrintABCDemo {

    public static void main(String[] args) {
        // 定义三个线程，让它们分别打印 A B C
        Data2 data2 = new Data2();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data2.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data2.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data2.printC();
            }
        }, "C").start();
    }
}

class Data2 {
    private static final Lock lock = new ReentrantLock();
    private static final Condition printA = lock.newCondition();
    private static final Condition printB = lock.newCondition();
    private static final Condition printC = lock.newCondition();

    private volatile int number = 0 ;  // 0 A, 1 B, 2 C

    // 打印A
    public void printA() {
        lock.lock();
        try {
            while (this.number != 0) {
                printA.await();
            }
            System.out.println(Thread.currentThread().getName()+"----> AAAA");
            this.number = 1;
            printB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 打印B
    public void printB() {
        lock.lock();
        try {
            while (this.number != 1) {
                printB.await();
            }
            System.out.println(Thread.currentThread().getName()+"----> BBBB");
            this.number = 2;
            printC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 打印C
    public void printC() {
        lock.lock();
        try {
            while (this.number != 2) {
                printC.await();
            }
            System.out.println(Thread.currentThread().getName()+"----> CCCC");
            this.number = 0;
            printA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



}
