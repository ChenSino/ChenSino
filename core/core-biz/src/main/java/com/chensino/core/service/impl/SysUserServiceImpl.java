package com.chensino.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.service.SysUserService;
import com.chensino.core.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author chenkun
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2022-09-01 09:57:34
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




