package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-08 17:39
 * @Description
 **/
@Data
public class OrderInfoSearchVO<T> extends PageUtil<T> {
    /**
     * 标题
     */
    private String title;
}
