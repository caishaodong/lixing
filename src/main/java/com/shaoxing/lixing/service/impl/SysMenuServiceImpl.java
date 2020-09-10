package com.shaoxing.lixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxing.lixing.domain.entity.SysMenu;
import com.shaoxing.lixing.domain.vo.SysMenuVO;
import com.shaoxing.lixing.global.enums.YesNoEnum;
import com.shaoxing.lixing.global.util.MenuUtil;
import com.shaoxing.lixing.mapper.SysMenuMapper;
import com.shaoxing.lixing.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-09-10
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 获取菜单列表（不分页）
     *
     * @return
     */
    @Override
    public List<SysMenuVO> getList() {
        List<SysMenu> sysMenuList = this.baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getIsEnable, YesNoEnum.YES.getValue())
                .eq(SysMenu::getIsDeleted, YesNoEnum.NO.getValue()).orderByAsc(SysMenu::getSortNum));
        // 复制list
        List<SysMenuVO> sysMenuVOList = new ArrayList<>();
        MenuUtil.copyList(sysMenuList, sysMenuVOList);
        // 获取菜单树
        List<SysMenuVO> menuTree = MenuUtil.getMenuTree(sysMenuVOList);

        return menuTree;
    }


}
