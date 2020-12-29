package com.middleyun;

import org.junit.Test;

import java.util.concurrent.*;

public class FutureTask {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void futureTask() throws InterruptedException, ExecutionException, TimeoutException {
        Future<Integer> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(15);
            return 10;
        });
        Integer value = future.get(10, TimeUnit.SECONDS);
        System.out.println(value);
    }
}
