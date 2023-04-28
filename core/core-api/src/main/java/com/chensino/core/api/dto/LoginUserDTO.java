package com.chensino.core.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @Description TODO
 * @Date 2023/4/28 上午11:03
 * @Created by chenxk
 */
@Data
public class LoginUserDTO {

    /**
     * 枚举类型的name,必须和LoginTypeEnum名一致 取值GITHUB/USERNAME/PHONE等
     */
    @NotNull(message = "登录类型不能为空")
    private String loginType;

    //帐号密码登录
    private String username;
    private String password;

    //手机号验证码登录
    private String phone;
    private String phoneCode;

}
