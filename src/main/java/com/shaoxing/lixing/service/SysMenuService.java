package com.shaoxing.lixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shaoxing.lixing.domain.entity.SysMenu;
import com.shaoxing.lixing.domain.vo.SysMenuVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-10
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单列表（不分页）
     *
     * @return
     */
    List<SysMenuVO> getList();
}
