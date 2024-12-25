package com.iomc.common.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 当前日期字符串
     */
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }


    /**
     * 获取当前日期时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前日期字符串
     */
    public static String getCurrentDateTime() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDate.format(formatter);
    }
}
