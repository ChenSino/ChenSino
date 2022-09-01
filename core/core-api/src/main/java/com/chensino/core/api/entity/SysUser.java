package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 工号
     */
    private String employId;

    /**
     * 
     */
    private Integer password;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}