package com.middleyun.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock Condition
 *
 * 模拟生产者和消费者， 生产一个消费一个
 *
 */
public class ConditionDemo {


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
class Task {
    private final Lock lock = new ReentrantLock();
    private final Condition producer = lock.newCondition();
    private final Condition consumer = lock.newCondition();

    // 任务数量
    private int taskNum;

    // 生产任务
    public void increment() {
        lock.lock();
        try {
            while (this.taskNum == 1) {
                producer.await();
            }
            this.taskNum ++;
            System.out.println(Thread.currentThread().getName() + "-->生产了一个任务，当前任务数量为：" + this.taskNum);
            consumer.signal();
            System.out.println("唤醒消费者");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费任务
    public void decrement() {
        lock.lock();
        try {
            while (this.taskNum == 0) {
                consumer.await();
            }
            this.taskNum --;
            System.out.println(Thread.currentThread().getName() + "-->消费了一个任务，当前任务数量为：" + this.taskNum);
            producer.signal();
            System.out.println("唤醒生产者");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
