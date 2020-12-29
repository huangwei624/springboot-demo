package com.middleyun.juc;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 学习Future 的异步回调
 */
public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        simpleFuture();
        completableFuture();
    }

    /**
     * Future 的一般使用方式
     */
    private static void simpleFuture() throws ExecutionException, InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(availableProcessors, availableProcessors + 1
                , 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(8), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        Future<Integer> future = threadPoolExecutor.submit(() -> 1024);
        System.out.println(future.get());   // 长时间阻塞获取结果，并没有实现真正的异步
    }

    /**
     * Future 真正的异步实现
     *
     */
    private static void completableFuture() throws ExecutionException, InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(availableProcessors, availableProcessors + 1
                , 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(8), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync ---> void");
        }, threadPoolExecutor).whenComplete((value, exception) -> {
            System.out.println("void value : " + value);
            System.out.println("void exception : " + exception);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("runAsync void executed");

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync ----> Integer");
            return 1024;
        }, threadPoolExecutor).whenComplete((value, exception)-> {
            System.out.println("integer value : " + value);
            System.out.println("integer exception : " + exception);
        });
        System.out.println("supplyAsync Integer executed");
    }
}
