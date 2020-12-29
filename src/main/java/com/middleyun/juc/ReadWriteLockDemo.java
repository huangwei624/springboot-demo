package com.middleyun.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
//        MyCacha myCacha = new MyCacha();
        MyCacha2 myCacha = new MyCacha2();
        for (int i = 0; i < 5; i++) {
            final int m =i;
            new Thread(()->{
                try {
                    myCacha.write(String.valueOf(m), UUID.randomUUID().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        for (int i = 5; i < 10; i++) {
            final int m =i;
            new Thread(()->{
                try {
                    myCacha.read(String.valueOf(m));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * 自定义缓存类, 可以同时读取数据， 但是同时写和边写边读不能同时进行
 */
class MyCacha2 {

    private volatile Map<String, Object> data = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 存数据
     * @param key
     * @param value
     */
    void write(String key, Object value) throws InterruptedException {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始存入一个数据，");
            this.data.put(key, value);
            TimeUnit.MILLISECONDS.sleep(10);
            System.out.println(Thread.currentThread().getName() + "数据存入完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读取数据
     * @param key
     */
    Object read(String key) throws InterruptedException {
        readWriteLock.readLock().lock();
        Object value = null;
        try {
            System.out.println(Thread.currentThread().getName() + "开始读取一个数据，");
            value = this.data.get(key);
            TimeUnit.MILLISECONDS.sleep(10);
            System.out.println(Thread.currentThread().getName() + "数据读取完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return value;
    }

}

class MyCacha {

    private volatile Map<String, Object> data = new HashMap<>();

    /**
     * 存数据
     * @param key
     * @param value
     */
    void write(String key, Object value) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开始存入一个数据，");
        this.data.put(key, value);
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(Thread.currentThread().getName() + "数据存入完成！");
    }

    /**
     * 读取数据
     * @param key
     */
    Object read(String key) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开始读取一个数据，");
        Object value = this.data.get(key);
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(Thread.currentThread().getName() + "数据读取完成！");
        return value;
    }

}
