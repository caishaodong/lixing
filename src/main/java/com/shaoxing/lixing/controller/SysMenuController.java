package com.shaoxing.lixing.controller;


import com.shaoxing.lixing.domain.vo.SysMenuVO;
import com.shaoxing.lixing.global.ResponseResult;
import com.shaoxing.lixing.global.base.BaseController;
import com.shaoxing.lixing.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理
 *
 * @author caishaodong
 * @since 2020-09-10
 */
@RestController
@RequestMapping("/sysMnu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取菜单列表（不分页）
     *
     * @return
     */
    @GetMapping("/getList")
    public ResponseResult<SysMenuVO> getList() {
        List<SysMenuVO> list = sysMenuService.getList();
        return success(list);
    }

}
