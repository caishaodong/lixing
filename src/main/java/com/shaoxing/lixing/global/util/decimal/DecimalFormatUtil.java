package com.shaoxing.lixing.global.util.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:27
 * @Description
 **/
public class DecimalFormatUtil {
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DECIMAL_FORMAT = new DecimalFormat();
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);
    }

    public static String format(String pattern, BigDecimal decimal) {
        if (pattern.contains("#.") && decimal.compareTo(BigDecimal.ZERO) >= 0 && decimal.compareTo(BigDecimal.ONE) < 1) {
            pattern = "0.00";
        }
        DECIMAL_FORMAT.applyPattern(pattern);
        return DECIMAL_FORMAT.format(decimal);
    }

    public static void main(String[] args) {
        System.out.println(format("####.##", new BigDecimal("0012.00")));// 12
        System.out.println(format("0000.00", new BigDecimal("0012.00")));// 0012.00
        System.out.println(format("#00.00", new BigDecimal("0012.00")));// 12.00
        System.out.println(format("000.00", new BigDecimal("0012.00")));// 012.00
        System.out.println(format("#.00", new BigDecimal("0012.00")));// 12.00
        System.out.println(format("#.00", new BigDecimal("0.01")));// 0.01
        System.out.println(format("0.00", new BigDecimal("0012.00")));// 12.00
        System.out.println(format("#.00", new BigDecimal("0.00")));// 0.00
    }
}
