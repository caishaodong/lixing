package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.entity.SysCity;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.service.SysCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省市区管理
 *
 * @author caishaodong
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/sysCity")
public class SysCityController extends BaseController {
    @Autowired
    private SysCityService sysCityService;

    /**
     * 根据地区编码获取地区名称
     *
     * @param code
     * @return
     */
    @GetMapping("/getNameByAreaCode/{code}")
    public ResponseResult getNameByAreaCode(@PathVariable(value = "code") Integer code) {
        String name = sysCityService.getNameByAreaCode(code);
        return success(name);
    }

    /**
     * 根据父级城市编码获取子级城市数据（获取省份时，parentCode传1）
     *
     * @param parentCode
     * @return
     */
    @GetMapping("/getCityListByParentCode/{parentCode}")
    public ResponseResult getCityListByParentCode(@PathVariable(value = "parentCode") Integer parentCode) {
        List<SysCity> cityList = sysCityService.getCityListByParentCode(parentCode);
        return success(cityList);
    }
}
