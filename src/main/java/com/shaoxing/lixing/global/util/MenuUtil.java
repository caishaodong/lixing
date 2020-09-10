package com.shaoxing.lixing.global.util;

import com.shaoxing.lixing.domain.entity.SysMenu;
import com.shaoxing.lixing.domain.vo.SysMenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author caishaodong
 * @Date 2020-09-10 15:09
 * @Description
 **/
public class MenuUtil {

    public static Integer FIRST_LEVEL = 1;

    /**
     * 复制list
     *
     * @param sysMenuList
     * @param sysMenuVOList
     */
    public static void copyList(List<SysMenu> sysMenuList, List<SysMenuVO> sysMenuVOList) {
        sysMenuVOList = Objects.isNull(sysMenuVOList) ? new ArrayList<>() : sysMenuVOList;
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return;
        }
        for (SysMenu sysMenu : sysMenuList) {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            sysMenuVO.setChildren(new ArrayList<SysMenuVO>());
            sysMenuVOList.add(sysMenuVO);
        }
        return;
    }

    /**
     * 获取菜单树
     *
     * @param sysMenuVOList
     */
    public static List<SysMenuVO> getMenuTree(List<SysMenuVO> sysMenuVOList) {
        // 获取所有的一级菜单
        List<SysMenuVO> firstLevelMenuList = sysMenuVOList.stream().filter(sysMenuVO -> sysMenuVO.getLevel().intValue() == FIRST_LEVEL.intValue())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(firstLevelMenuList)) {
            return null;
        }
        firstLevelMenuList.stream().forEach(firstLevelMenu -> {
            setChildren(sysMenuVOList, firstLevelMenu);
        });
        return firstLevelMenuList;
    }

    /**
     * 获取子菜单
     *
     * @param sysMenuVOList
     * @param parent
     */
    public static void setChildren(List<SysMenuVO> sysMenuVOList, SysMenuVO parent) {
        List<SysMenuVO> children = sysMenuVOList.stream().filter(sysMenuVO -> sysMenuVO.getPcode().equals(parent.getCode())).collect(Collectors.toList());
        parent.setChildren(children);
        if (!CollectionUtils.isEmpty(children)) {
            for (SysMenuVO child : children) {
                setChildren(sysMenuVOList, child);
            }
        }

    }
}
