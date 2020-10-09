package com.shaoxing.lixing.global.util.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author caishaodong
 * @Date 2020-10-09 16:23
 * @Description
 **/
public class BusinessUtil {
    /**
     * 获取首页销售统计导出Excel的追加行
     * @param totalAmount
     * @return
     */
    public static List<Map<Integer, Object>> getIndexStatisticsExportTailMapList(BigDecimal totalAmount) {
        List<Map<Integer, Object>> tailMapList = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>(16);
        map.put(0, "总计");
        map.put(7, totalAmount);
        tailMapList.add(map);
        return tailMapList;
    }

    /**
     * 获取订单列表导出Excel的追加行
     * @param deliveryUserName
     * @param checkUserName
     * @param totalAmount
     * @return
     */
    public static List<Map<Integer, Object>> getOrderInfoExportTailMapList(String deliveryUserName, String checkUserName, BigDecimal totalAmount) {
        List<Map<Integer, Object>> tailMapList = new ArrayList<>();
        Map<Integer, Object> map1 = new HashMap<>(16);
        map1.put(0, "总计");
        map1.put(9, totalAmount);
        tailMapList.add(map1);
        Map<Integer, Object> map2 = new HashMap<>(16);
        map2.put(1, "送货人：");
        map2.put(2, deliveryUserName);
        map2.put(5, "验收人：");
        map2.put(6, checkUserName);
        tailMapList.add(map2);
        return tailMapList;
    }

    /**
     * 获取公司账单导出Excel的追加行
     * @param totalAmount
     * @return
     */
    public static List<Map<Integer, Object>> getCompanyBillExportTailMapList(BigDecimal totalAmount) {
        List<Map<Integer, Object>> tailMapList = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>(16);
        map.put(0, "总计");
        map.put(8, totalAmount);
        tailMapList.add(map);
        return tailMapList;
    }
}
