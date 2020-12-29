package com.middleyun.juc;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {

    public static void main(String[] args) {
//        hashSet();
        copyOnWriteArraySet();
    }

    private static void copyOnWriteArraySet() {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString());
                System.out.println(set);
            }).start();
        }
    }

    private static void hashSet() {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString());
                System.out.println(set);
            }).start();
        }
    }
}
