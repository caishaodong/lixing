package com.shaoxing.lixing.global.util.excel;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:20
 * @Description 定义表格的数据对象
 **/
@Data
public class ExcelSheetPO {
    /**
     * sheet的名称
     */
    private String sheetName;

    /**
     * 表格标题
     */
    private String title;

    /**
     * 头部标题集合
     */
    private String[] headers;

    /**
     * 数据集合
     */
    private List<List<Object>> dataList;
    /**
     * 行宽
     */
    private Map<Integer, Integer> columnWidthMap;
    /**
     * 是否需要序号
     */
    private Boolean needSort = Boolean.FALSE;
    /**
     * 追加行
     */
    private List<Map<Integer, Object>> tailMapList;


}
