package com.middleyun.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("7颗龙珠已经集齐，开始召唤神龙！");
        });

        for (int i = 0; i < 7; i++) {
            final int m = i;
            new Thread(()->{
                try {
                    System.out.println("找到第"+(m+1)+"颗龙珠！");
                    TimeUnit.SECONDS.sleep(2);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
