package com.middleyun.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Java8Date {

    @Test
    public void local2Date() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(dateFormatter.format(LocalDate.now()));
        java.util.Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(dateTimeFormatter.parse(dateTimeFormatter.format(LocalDateTime.now())));

        Instant now = Instant.now();
        System.out.println(now);
    }
}
