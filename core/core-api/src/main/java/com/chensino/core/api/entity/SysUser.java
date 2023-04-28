package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体，不直接继承{@link org.springframework.security.core.userdetails.User}是因为此实体是直接和数据库字段
 * 有映射关系，为避免麻烦，再另外创建一个类，继承{@link org.springframework.security.core.userdetails.User}
 * @see CustomSecurityUser
 * @TableName t_user
 */
@TableName(value ="t_user")
@ApiModel(value = "SysUser",description = "用户实体信息")
@Data
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String employId;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long creatorId;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标志")
    private Boolean delFlag;

    /**
     * 是否禁用
     */
    @ApiModelProperty(value = "是否禁用")
    private Boolean lockFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}