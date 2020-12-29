package com.middleyun;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程池测试
 */
public class ThreadPoolTest {


    @Test
    public void threadTaskThrowException() throws InterruptedException {
        int corePoolSizes = Runtime.getRuntime().availableProcessors();	// 4
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSizes, corePoolSizes + 1, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(threadPoolExecutor.getActiveCount() +",  " + threadPoolExecutor.getQueue().size());
                throw new RuntimeException("抛出异常");
            });
            TimeUnit.SECONDS.sleep(4);
        }
    }

    @Test
    public void threadTaskFuture() throws InterruptedException {
        int corePoolSizes = Runtime.getRuntime().availableProcessors();	// 4
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSizes, corePoolSizes + 1, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));
        Future<Boolean> future = threadPoolExecutor.submit(() -> {
            TimeUnit.SECONDS.sleep(20);
            return false;
        });
        Boolean aBoolean = null;
        try {
            aBoolean = future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                System.out.println(e);
            }
        }
        System.out.println(aBoolean);
    }

    Boolean timeoutTask(String print)  {

        try {
            HttpClientUtil.get(HttpConfig.custom().url("http://localhost:8080/timeout"));
        } catch (HttpProcessException e) {
        }

//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    @Test
    public void timeoutTaskSubmit() throws InterruptedException {
        int corePoolSizes = Runtime.getRuntime().availableProcessors();	    // 4
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(7, 12, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            Future<Boolean> future = threadPoolExecutor.submit(() -> {
                try {
                    timeoutTask("=================");
                } catch (Exception e) {

                }
                return true;
            });
            Boolean aBoolean = null;
            try {
                // System.out.println(threadPoolExecutor.getActiveCount());
                aBoolean = future.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                if (e instanceof TimeoutException) {
                    boolean cancel = future.cancel(true);
                    System.out.println("中断线程： " + cancel);
                    System.out.println(threadPoolExecutor.getActiveCount());
                }
            }
        }
        Thread.sleep(1000000000);
    }


    void print() throws HttpProcessException {
        HttpClientUtil.get(HttpConfig.custom().url("http://localhost:8080/timeout"));
    }

    @Test
    public void print2() throws HttpProcessException {
        for (int i = 0; i < 20; i++) {
            HttpClientUtil.get(HttpConfig.custom().url("http://localhost:8080/timeout"));
        }
    }

    @Test
    public void pT() throws HttpProcessException, IOException {
        File file = new File("G:\\hw\\ui\\security-ui\\README.md");
        System.out.println(file.lastModified());
        Path path = Paths.get("G:\\hw\\ui\\security-ui\\README.md");
        BasicFileAttributeView attributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicFileAttributes = attributeView.readAttributes();
        long createTime = basicFileAttributes.lastModifiedTime().to(TimeUnit.MILLISECONDS);
        System.out.println(createTime);
        // print2();

        UserPrincipal owner = Files.getFileAttributeView(path, FileOwnerAttributeView.class).getOwner();
        System.out.println(owner.getName());
    }


    @Test
    public void printTest() throws InterruptedException {
        int corePoolSizes = Runtime.getRuntime().availableProcessors();	    // 4
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSizes, corePoolSizes+1, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(5));

        try {
            threadPoolExecutor.execute(()->{
                try {
                    int a = 5 / 0;
                } catch (Exception e) {
                    throw new RuntimeException("abc");
                }
            });
            // 无法拿到线程池中的抛出的异常
        } catch (Exception e) {
            System.out.println(e.getMessage() + " ===========");
        }

    }

    @Test
    public void test3() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("name", 123);
        map.put("age", 456);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            if (next.getValue() == 123) {
//                map.remove(next.getKey());
                iterator.remove();
                System.out.println(next.getValue());
            }
        }
        System.out.println(map);
    }

    ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();
    ReentrantLock lock = new ReentrantLock();
//    ReentrantLock locks = new ReentrantLock();
    public void test4() {
        ReentrantLock reentrantLock = locks.get("1234");
        if (reentrantLock == null) {
            locks.put("1234", new ReentrantLock());
        }
        locks.get("1234").lock();
//        locks.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread()+"****");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locks.get("1234").unlock();
//            locks.unlock();
        }
    }

    @Test
    public void test5() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                test4();
            }, i+"---").start();
        }

        Thread.sleep(2000);
    }
}
