package com.middleyun.juc;

/**
 * 单例模式
 */
public class SingleDemo {
    public static void main(String[] args) {
        LazyMan.getInstance();
    }
}

/**
 * 饿汉式
 */
class Hunger {
    private static final Hunger hunger = new Hunger();

    private Hunger() {

    }

    public static Hunger getInstance() {
        return hunger;
    }
}

/**
 * 懒汉式
 */
class LazyMan {
    private static volatile LazyMan lazyMan;

    private LazyMan() {

    }

    public static LazyMan getInstance() {
        if (lazyMan == null) {
            synchronized (LazyMan.class) {
                if (lazyMan == null) {
                    /**
                     * lazyMan = new LazyMan(); 是不安全的，过程分为三个步骤：
                     * 1. 开辟内存空间
                     * 2. 在内存空间中初始化对象
                     * 3. lazyMan 指向该内存空间
                     */
                    lazyMan = new LazyMan();
                    // 当加入了volatile 关键字后, lazyMan 相关操作的前后会加上内存屏障，
                    // 屏障上和lazyMan 之间不可以进行重排序, 屏障下和lazyMan之间不可以进行重排序
                }
            }
        }
        return lazyMan;
    }

}

/**
 * 静态内部类
 */
class Holder {

    public static InnerClass getInstance() {
        return InnerClass.instance;
    }

    private static class InnerClass {
        private static final InnerClass instance = new InnerClass();

        private InnerClass() {

        }
    }
}

/**
 * 枚举
 */

enum SingleEnum {
    INSTANCE;

    private SingleEnum() {

    }
}


