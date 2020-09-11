package com.shaoxing.lixing.global.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:08
 * @Description
 **/
public class LocalDateTimeUtil {

    /**
     * 获取今日日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongToday() {
        String today = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        return Long.parseLong(today);
    }

    /**
     * 获取昨日日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongYesterday() {
        String today = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now().plusDays(-1));
        return Long.parseLong(today);
    }

    /**
     * 获取本周一日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongThisMonday() {
        LocalDate thisMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        String thisMondayStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(thisMonday);
        return Long.parseLong(thisMondayStr);
    }

    /**
     * 获取上周一日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongLastMonday() {
        LocalDate thisMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate lastMonday = thisMonday.plusDays(-7);
        String lastMondayStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(lastMonday);
        return Long.parseLong(lastMondayStr);
    }

    public static void main(String[] args) {
        System.out.println(getLongToday());
        System.out.println(getLongYesterday());
        System.out.println(getLongThisMonday());
        System.out.println(getLongLastMonday());
    }
}
