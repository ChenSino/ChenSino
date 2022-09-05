package com.chensino.core.api.dto;

import com.chensino.core.api.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "用户信息")
@Data
public class UserInfo implements Serializable {

    /**
     * 用户基本信息
     */
    @ApiModelProperty(value = "用户基本信息")
    private SysUser sysUser;

    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色集合")
    private Integer[] roles;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String[] permissions;
}
