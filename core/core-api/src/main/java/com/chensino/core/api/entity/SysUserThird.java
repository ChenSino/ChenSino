package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 当前系统用户和第三方系统（比如oauth）的映射关系
 * @Date 2023/5/4 下午5:18
 * @Created by chenxk
 */
@TableName(value ="t_user_third")
@ApiModel(value = "SysUserThird",description = "第三方用户和本地用户映射")
@Data
public class SysUserThird {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    //第三方系统（可能多个，所以用项目区分）
    private String project;

    //本地用户名
    private String username;

    //第三方用户名
    private String usernameThird;

}
