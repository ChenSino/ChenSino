package com.chensino.common.data.configuration.constant;

/**
 * redis常量设置
 */
public interface CacheConst {
    String ACCESS_TOKEN_PREFIX = "chensino:access_token";

    //手机登录时发送的验证码
    String SMS_CODE_LOGIN_PREFIX = "chensino:login:sms_code";

    //人体生命注册时发送的验证码
    String SMS_CODE_REGISTER_PREFIX = "chensino:register:sms_code";

}
