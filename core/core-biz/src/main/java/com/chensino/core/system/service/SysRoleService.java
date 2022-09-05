package com.chensino.core.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chensino.core.api.entity.SysRole;

import java.util.List;

/**
* @author chenkun
* @description 针对表【t_role(角色表)】的数据库操作Service
* @createDate 2022-09-05 14:19:12
*/
public interface SysRoleService extends IService<SysRole> {
    List<SysRole> listRolesByUserId(Long userId);
}
