package com.shaoxing.lixing.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:01
 * @Description
 **/
public class OrderNoUtils {
    protected static final String pattern = "yyyyMMdd";
    protected static final DateTimeFormatter formatter;
    static OrderNoUtils orderNoUtils;

    public static OrderNoUtils getInstance() {
        if (OrderNoUtils.orderNoUtils == null) {
            return OrderNoUtils.orderNoUtils = new OrderNoUtils();
        }
        return OrderNoUtils.orderNoUtils;
    }

    public static String getSerialNumber() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return OrderNoUtils.formatter.format(LocalDateTime.now()).substring(2, 8) + String.format("%010d", hashCode);
    }

    static {
        formatter = DateTimeFormatter.ofPattern(pattern);
        OrderNoUtils.orderNoUtils = new OrderNoUtils();
    }

    public static void main(String[] args) {
        System.out.println(getSerialNumber());
    }
}
