package com.chensino.core.api.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Date 2023/4/28 上午11:03
 * @Created by chenxk
 */
@Data
//@ApiModel
public class UserLoginDTO {

    /**
     * 枚举类型的name,必须和LoginTypeEnum名一致 取值GITHUB/USERNAME/PHONE等
     */
    //--------------------@Controller层校验------------------
    @NotBlank(message = "登录类型不能为空")
//    @ApiModelProperty("登录类型")
    private String loginType;

    //---------------------自定义validator校验----------------
    //帐号密码登录
    private String username;
    private String password;

    //手机号验证码登录
    private String phone;
    private String phoneCode;

}

