package com.shaoxing.lixing.global.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020/9/28 22:23
 * @Description
 **/
public class ListUtil {
    /**
     * 两个list取差集
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> List<T> getSubtraction(List<T> list1, List<T> list2) {
        list1 = CollectionUtils.isEmpty(list1) ? Collections.emptyList() : list1;
        list2 = CollectionUtils.isEmpty(list2) ? Collections.emptyList() : list2;
        List<T> copyList1 = new ArrayList<>(list1);
        List<T> copyList2 = new ArrayList<>(list2);

        copyList1.removeAll(copyList2);
        return copyList1;
    }

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>() {{
            add("A");
            add("B");
        }};
        List<String> list2 = new ArrayList<String>() {{
            add("B");
            add("C");
        }};

        System.out.println(getSubtraction(list1, list2));
        System.out.println(getSubtraction(list2, list1));
    }
}
