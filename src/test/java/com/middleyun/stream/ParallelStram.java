package com.middleyun.stream;

import com.carrotsearch.hppc.LongIndexedContainer;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.LongStream;

public class ParallelStram {

    @Test
    public void parallelCompute() {
        System.out.println(Long.MAX_VALUE);
        Long num = 100000000L;
        long start = System.currentTimeMillis();
        Long temp =0L;
        for (Long i = 0L; i <= num; i++) {
            temp += i;
        }
        System.out.println(temp);
        long part1 = System.currentTimeMillis();
        System.out.println("part1: " + (part1 - start));
        part1 = System.currentTimeMillis();
        Long[] tp = new Long[]{0L};
//        System.out.println(LongStream.rangeClosed(0, num).parallel().sum());
        LongStream.rangeClosed(0, num).parallel().forEachOrdered(item -> {
            tp[0] += item;
        });
        System.out.println(tp[0]);
        long end = System.currentTimeMillis();
        System.out.println("end: " + (end - part1));

    }

}
