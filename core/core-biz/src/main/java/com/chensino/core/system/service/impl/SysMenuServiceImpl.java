package com.chensino.core.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chensino.core.api.entity.SysMenu;
import com.chensino.core.system.service.SysMenuService;
import com.chensino.core.system.mapper.SysMenuMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenkun
* @description 针对表【t_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2022-09-05 15:51:54
*/
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{
private final SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> listMenusByRoleId(Integer roleId) {
        return sysMenuMapper.listMenusByRoleId(roleId);
    }
}




