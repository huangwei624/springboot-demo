package com.middleyun.collection;

import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * @title HashMap 测试
 * @description
 * @author huangwei
 * @createDate 2021/2/18
 * @version 1.0
 */
public class HashMapTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        HashMap<String, Object> map = new HashMap<>(12);
        map.put("hello", 12);
        IntStream.rangeClosed(1, 10000000).forEach(item ->{
            byte[] bytes = new byte[1024];
        });
        System.out.println(System.currentTimeMillis() - start);
    }

}
