package com.chensino.core.system.service;

import com.chensino.core.api.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenkun
* @description 针对表【t_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-09-05 15:51:54
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 通过角色编号查询菜单
     * @param roleId 角色ID
     * @return
     */
    List<SysMenu> listMenusByRoleId(Integer roleId);
}
