package com.chensino.core.system.service;

import com.chensino.core.api.dto.UserInfo;
import com.chensino.core.api.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenkun
* @description 针对表【t_user(用户表)】的数据库操作Service
* @Date 2022-09-01 09:57:34
*/
public interface SysUserService extends IService<SysUser> {

    UserInfo findUserInfo(SysUser sysUser);

    SysUser findUserByPhone(String phone);
}
