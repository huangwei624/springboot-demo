package com.middleyun.juc;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟生产者和消费者场景
 */
public class WaitNotifyDemo {
    public static void main(String[] args) {
        Task task = new Task();

        // 生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    task.increment();
                    TimeUnit.MILLISECONDS.sleep(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "producer").start();

        // 消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    task.decrement();
                    TimeUnit.MILLISECONDS.sleep(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();


    }
}


/**
 * 生产者和消费者作用的对象
 */
class Task1 {
    private final Object object = new Object();

    // 任务数量
    private int taskNum;

    // 生产任务
    public void increment() throws InterruptedException {
        synchronized (this.object) {
            while (this.taskNum == 1) {
                object.wait();
            }
            this.taskNum ++;
            System.out.println(Thread.currentThread().getName() + "-->生产了一个任务，当前任务数量为：" + this.taskNum);
            this.object.notify();
        }
    }

    // 消费任务
    public void decrement() throws InterruptedException {
        synchronized (this.object) {
            while (this.taskNum == 0) {
                this.object.wait();
            }
            this.taskNum --;
            System.out.println(Thread.currentThread().getName() + "-->消费了一个任务，当前任务数量为：" + this.taskNum);
            this.object.notify();
        }
    }

}



