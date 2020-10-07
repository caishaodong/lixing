package com.shaoxing.lixing.global.util.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020/10/7 22:25
 * @Description
 **/
@Data
@AllArgsConstructor
public class ExcelDataDTO<T> {
    /**
     * 标题
     */
    private String title;
    /**
     * 列名（key:列名，value:[属性名，列宽]）
     */
    private LinkedHashMap<String, String[]> fieldNameMap;
    /**
     * 数据列表
     */
    private List<T> dataList;
    /**
     * 导出文件名
     */
    private String fileName;
    /**
     * 是否需要序号列
     */
    private Boolean needSort;
}
