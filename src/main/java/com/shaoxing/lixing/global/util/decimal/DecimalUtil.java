package com.shaoxing.lixing.global.util.decimal;


import com.shaoxing.lixing.global.constant.NumberConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:27
 * @Description
 **/
public class DecimalUtil {


    private static final String FORMAT_1 = "#.00";
    public final static String FORMAT_2 = "0.00";

    public static BigDecimal ifNullAS0(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }


    public static BigDecimal ifNullASDefault(BigDecimal bigDecimal, BigDecimal defaultDecimal) {
        return bigDecimal == null ? defaultDecimal : bigDecimal;
    }

    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        return multiply(b1, b2, 2);
    }

    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2, Integer scale) {
        return ifNullAS0(b1).multiply(ifNullAS0(b2)).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal multiply_divide100(BigDecimal b1, BigDecimal b2) {
        return divide(multiply(b1, b2, 2), NumberConstants.BIG_DECIMAL_100);
    }


    public static BigDecimal divide_multiply100(BigDecimal b1, BigDecimal b2) {
        return multiply(divide(b1, b2), NumberConstants.BIG_DECIMAL_100);
    }

    public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        return divide(b1, b2, 2);
    }

    public static BigDecimal divide(BigDecimal b1, BigDecimal b2, Integer scale) {
        b2 = ifNullAS0(b2);
        if (NumberConstants.BIG_DECIMAL_0.compareTo(b2) == 0) {
            throw new IllegalArgumentException("The dividend can't be 0");
        }
        return ifNullAS0(b1).divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return add(b1, b2, 2);
    }

    public static BigDecimal add(BigDecimal b1, BigDecimal b2, Integer scale) {
        return ifNullAS0(b1).add(ifNullAS0(b2)).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        return subtract(b1, b2, 2);
    }

    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2, Integer scale) {
        return ifNullAS0(b1).subtract(ifNullAS0(b2)).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal avg(BigDecimal b1, BigDecimal b2, Integer scale) {
        return ifNullAS0(b1).add(ifNullAS0(b2)).divide(NumberConstants.BIG_DECIMAL_2, scale, BigDecimal.ROUND_HALF_UP);
    }


    public static String format(BigDecimal var1) {
        if (var1 == null) {
            return "";
        }
        return new DecimalFormat(FORMAT_2).format(var1);
    }


    public static String format(BigDecimal var1, int scale) {
        if (var1 == null) {
            return "";
        }
        return new DecimalFormat(fillZero(scale)).format(var1);
    }

    public static String format(BigDecimal var1, String pattern) {
        if (var1 == null) {
            return "";
        }
        return new DecimalFormat(pattern).format(var1);
    }

    public static String format(BigDecimal var1, String pattern, int scale) {
        if (var1 == null) {
            return "";
        }
        return new DecimalFormat(FORMAT_2).format(var1);
    }

    private static String fillZero(int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale must > 0");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("0");
        if (scale == 0) {
            return builder.toString();
        } else {
            builder.append(".");
            for (int i = 0; i < scale; i++) {
                builder.append("0");
            }
            return builder.toString();
        }
    }

    public static boolean ge0(BigDecimal var1) {
        return ge(var1, BigDecimal.ZERO);
    }

    public static boolean le0(BigDecimal var1) {
        return le(var1, BigDecimal.ZERO);
    }

    public static boolean gt0(BigDecimal var1) {
        return gt(var1, BigDecimal.ZERO);
    }

    public static boolean lt0(BigDecimal var1) {
        return lt(var1, BigDecimal.ZERO);
    }

    public static boolean eq0(BigDecimal var1) {
        return eq(var1, BigDecimal.ZERO);
    }


    /**
     * left greater than right
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean gt(BigDecimal left, BigDecimal right) {
        assertNotNull(left);
        assertNotNull(right);
        return left.compareTo(right) > 0;
    }

    /**
     * left greater than or equal with right
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean ge(BigDecimal left, BigDecimal right) {
        assertNotNull(left);
        assertNotNull(right);
        return left.compareTo(right) >= 0;
    }

    /**
     * left less than right
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean lt(BigDecimal left, BigDecimal right) {
        assertNotNull(left);
        assertNotNull(right);
        return left.compareTo(right) < 0;
    }

    /**
     * left less than right
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean le(BigDecimal left, BigDecimal right) {
        assertNotNull(left);
        assertNotNull(right);
        return left.compareTo(right) <= 0;
    }

    /**
     * left equal with right
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean eq(BigDecimal left, BigDecimal right) {
        assertNotNull(left);
        assertNotNull(right);
        return left.compareTo(right) == 0;
    }


    public static void assertNotNull(BigDecimal var) {
        if (var == null) {
            throw new NullPointerException("var is null !!!");
        }
    }

    public static String formatPretty(BigDecimal bigDecimal) {
       return new DecimalFormat((bigDecimal.compareTo(BigDecimal.ZERO) >= 0 && bigDecimal.compareTo(BigDecimal.ONE) < 1) ? FORMAT_2 : FORMAT_1)
                .format(bigDecimal);
    }

    public static void main(String[] args) {
        System.out.println(formatPretty(new BigDecimal("2")));
    }
}
