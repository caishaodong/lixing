package com.shaoxing.lixing.domain.vo;

import com.shaoxing.lixing.domain.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-09-10 14:48
 * @Description
 **/
@Data
public class SysMenuVO extends SysMenu {
    List<SysMenuVO> children;
}
