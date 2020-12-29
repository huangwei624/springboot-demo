package com.middleyun.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws InterruptedException {
//        arrayList();
        copyOnWriteArrayList();
    }

    private static void copyOnWriteArrayList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
//        Collections.synchronizedList()
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                list.add("hello");
                System.out.println(list);
            }).start();
        }
    }

    private static void arrayList() throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                list.add("hello");
                System.out.println(list);
            }).start();
        }
    }

}
