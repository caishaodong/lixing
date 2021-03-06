package com.shaoxing.lixing.global.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-08-06 17:07
 * @Description
 **/
public class PageUtil<T> implements IPage<T> {

    /**
     * 记录列表
     */
    private List<T> records;
    /**
     * 总数量
     */
    private long total;
    /**
     * 每页的数量
     */
    private long size = 10;
    /**
     * 页码
     */
    private long current = 1;

    @Override
    public List<OrderItem> orders() {
        return null;
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
