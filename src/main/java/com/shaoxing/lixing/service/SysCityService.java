package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.entity.SysCity;

import java.util.List;

/**
 * <p>
 * 中国省市区地名表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-08
 */
public interface SysCityService extends IService<SysCity> {

    /**
     * 根据地区编码获取地区名称
     *
     * @param code
     * @return
     */
    String getNameByAreaCode(Integer code);

    /**
     * 根据父级城市编码获取子级城市数据
     *
     * @param parentCode
     * @return
     */
    List<SysCity> getCityListByParentCode(Integer parentCode);
}
