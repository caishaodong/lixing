package com.shaoxing.lixing.domain.dto;

import com.shaoxing.lixing.global.util.PageUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-09 18:06
 * @Description
 **/
@Data
public class CompanyBillSearchDTO extends PageUtil {
    public boolean paramCheck() {
        return true;
    }
}
