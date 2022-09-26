package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 角色表
 *
 * @TableName t_role
 */
@TableName(value = "t_role")
@Data
@ApiModel
public class SysRole implements Serializable {
    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 角色代码
     */
    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 数据权限类型，全部、本级、本级和子级、自定义
     */
    @ApiModelProperty(value = "数据权限")
    private String dsType;

    /**
     * 数据权限范围描述
     */
    @ApiModelProperty(value = "数据权限范围描述")
    private String dsScope;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole sysRole = (SysRole) o;
        return Objects.equals(roleId, sysRole.roleId) && Objects.equals(roleName, sysRole.roleName) && Objects.equals(roleCode, sysRole.roleCode) && Objects.equals(roleDesc, sysRole.roleDesc) && Objects.equals(dsType, sysRole.dsType) && Objects.equals(dsScope, sysRole.dsScope) && Objects.equals(createTime, sysRole.createTime) && Objects.equals(updateTime, sysRole.updateTime) && Objects.equals(delFlag, sysRole.delFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, roleCode, roleDesc, dsType, dsScope, createTime, updateTime, delFlag);
    }
}