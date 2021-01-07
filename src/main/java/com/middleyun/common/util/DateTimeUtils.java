package com.middleyun.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @title 日期格式化工具
 * @description
 * @author huangwei
 * @createDate 2021/1/6
 * @version 1.0
 */
public class DateTimeUtils {

    /**
     * 日期时间默认格式化方式
     */
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * 日期默认格式化方式
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 格式化LocalDateTime
     * @param localDateTime
     * @return
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).format(localDateTime);
    }

    /**
     * 时间解析
     * @param dateTime
     * @return
     */
    public static TemporalAccessor parseDateTime(String dateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).parse(dateTime);
    }

    /**
     * 格式化LocalDate
     * @param localDate
     * @return
     */
    public static String formatDateTime(LocalDate localDate) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDate);
    }

    /**
     * 日期解析
     * @param date
     * @return
     */
    public static TemporalAccessor parseDate(String date) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).parse(date);
    }

}
