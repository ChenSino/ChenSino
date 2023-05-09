package com.chensino.core.api.dto;

import com.chensino.core.api.entity.SysRole;
import com.chensino.core.api.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Data
public class UserInfo implements Serializable {

    /**
     * 用户基本信息
     */
//    @ApiModelProperty(value = "用户基本信息")
    private SysUser sysUser;

    /**
     * 角色集合
     */
//    @ApiModelProperty(value = "角色集合")
    private Set<SysRole> roles;

    /**
     * 权限标识
     */
//    @ApiModelProperty(value = "权限标识")
    private String[] permissions;
}
