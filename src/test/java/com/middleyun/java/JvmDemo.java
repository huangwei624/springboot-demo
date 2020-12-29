package com.middleyun.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class JvmDemo {

    @Test
    public void jvmDemo() {
        ArrayList<Byte[]> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Byte[1024 * 1024]);
        }
    }
}
