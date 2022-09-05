package com.chensino.core.system.mapper;

import com.chensino.core.api.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author chenkun
* @description 针对表【t_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-09-05 15:51:54
* @Entity com.chensino.core.api.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listMenusByRoleId(Integer roleId);

    /**
     * 通过角色ID查询权限
     * @param roleIds Ids
     * @return
     */
    List<String> listPermissionsByRoleIds(String roleIds);
}




