package com.middleyun.juc;

import org.apache.tomcat.util.collections.SynchronizedQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 重新认识 ArrayBlockingQueue
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();
        blockingQueueDemo.synchronousQueue();
    }

    // 同步队列, 该队列不会存储数据，最好使用 put 和 take 这组api
    private void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                queue.put(2);   // 阻塞，等待别的线程拿到这个数据，
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        // queue.put(3);    // 阻塞
        System.out.println(queue.take());   // 阻塞，知道拿到了数据
    }

    // 无界队列
    private void linkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
        queue1.add(1);
        queue1.put(2);
        queue1.offer(3);
        queue1.offer(4, 2, TimeUnit.SECONDS);

        queue1.remove();
//        queue1.take();
//        queue1.poll();
//        queue1.poll(2, TimeUnit.SECONDS);
        System.out.println(queue1);
    }

    // 有界队列
    private void arrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(4);

        queue.add(1);
        queue.put(2);
        queue.offer(3);
        queue.offer(4, 2, TimeUnit.SECONDS);

        queue.remove();
        queue.take();
        queue.poll();
        queue.poll(2, TimeUnit.SECONDS);

        System.out.println(queue);
    }
}
