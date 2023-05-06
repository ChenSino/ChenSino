package com.chensino.core.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chensino.core.api.entity.SysRole;
import com.chensino.core.system.mapper.SysRoleMapper;
import com.chensino.core.system.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
* @author chenkun
* @description 针对表【t_role(角色表)】的数据库操作Service实现
* @Date 2022-09-05 14:19:12
*/
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;

    @Override
    public Set<SysRole> listRolesByUserId(Long userId) {
        return sysRoleMapper.listRolesByUserId(userId);
    }
}




