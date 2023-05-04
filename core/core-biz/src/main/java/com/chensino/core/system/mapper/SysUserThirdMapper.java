package com.chensino.core.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chensino.core.api.entity.SysUserThird;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Date 2023/5/4 下午5:23
 * @Created by chenxk
 */
public interface SysUserThirdMapper extends BaseMapper<SysUserThird> {

    SysUserThird selectByUsername( @Param("githubUsername") String githubUsername);
}
