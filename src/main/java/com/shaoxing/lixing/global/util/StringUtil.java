package com.shaoxing.lixing.global.util;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-08-06 18:09
 * @Description
 **/
public class StringUtil {
    public static boolean isBlank(String str) {
        return "".equals(str) || str == null;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean equals(String str1, String str2) {
        if (Objects.isNull(str1) || Objects.isNull(str2)) {
            return false;
        }
        return str1.equals(str2);
    }
}
