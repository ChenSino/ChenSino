package com.chensino.core.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chensino.core.api.dto.UserInfo;
import com.chensino.core.api.entity.SysMenu;
import com.chensino.core.api.entity.SysRole;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.system.mapper.SysRoleMapper;
import com.chensino.core.system.mapper.SysUserMapper;
import com.chensino.core.system.service.SysMenuService;
import com.chensino.core.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author chenkun
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2022-09-01 09:57:34
*/
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;

    private final SysMenuService sysMenuService;

    @Override
    public UserInfo findUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        Set<SysRole> sysRoles = sysRoleMapper.listRolesByUserId(sysUser.getUserId());
        //设置角色ID
        List<Integer> roleIds = sysRoles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        //设置角色
        userInfo.setRoles(sysRoles);
        //根据角色查权限，设置权限
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId->{
            List<String> permissionList = sysMenuService.listMenusByRoleId(roleId).stream()
                    .filter(role -> !StringUtils.isBlank(role.getPermission()))
                    .map(SysMenu::getPermission).collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public SysUser findUserByPhone(String phone) {
        return sysUserMapper.findUserByPhone(phone);
    }
}




