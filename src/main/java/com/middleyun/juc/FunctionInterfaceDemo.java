package com.middleyun.juc;

import java.beans.IntrospectionException;
import java.util.LongSummaryStatistics;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 学习 四大函数式接口
 * 1， Function  R apply(T t);
 * 2, Consumer  R apply(T t);
 * 3, Predicate boolean test(T t);
 * 4, Supplier  T get();
 */
public class FunctionInterfaceDemo {
    public static void main(String[] args) {
        function();
        consumer();
        predicate();
        supplier();

        IntStream.rangeClosed(1, 100)
                .filter(item -> item % 2 == 0)
                .limit(10).distinct()
                .map(item -> item * 100)
                .forEach(System.out::println);

        long startTime = System.currentTimeMillis();
        System.out.println(LongStream.rangeClosed(1, 1_000_000_000).parallel().sum());
        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * 供给型接口
     */
    private static void supplier() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 2048;
            }
        };
        System.out.println(supplier.get());
    }

    /**
     * 断言型接口
     */
    private static void predicate() {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 100;
            }
        };
        System.out.println(predicate.test(200));
    }


    /**
     * 消费型接口
     */
    private static void consumer() {
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        };
        consumer.accept(1024);
    }

    /**
     * 函数式接口
     */
    private static void function() {
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + "world";
            }
        };
        System.out.println(function.apply("hello "));
    }
}
