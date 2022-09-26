package com.chensino.core.system.mapper;

import com.chensino.core.api.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
* @author chenkun
* @description 针对表【t_role(角色表)】的数据库操作Mapper
* @createDate 2022-09-05 14:19:12
* @Entity com.chensino.core.api.entity.Role
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Set<SysRole> listRolesByUserId(Long userId);
}




