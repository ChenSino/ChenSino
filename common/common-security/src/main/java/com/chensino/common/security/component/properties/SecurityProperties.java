package com.chensino.common.security.component.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author chenkun
 * @Date 2023/5/5 上午10:30
 */
@ConfigurationProperties(prefix = "security")
@Data
@Configuration
public class SecurityProperties {
    /**
     * 白名单
     */
    private List<String> whiteList;

    @NestedConfigurationProperty
    private Token token = new Token();

    @Data
    public static class Token {
        //token过期时间,默认1小时
        private long accessTokenExpire = 3600;
        //token检测时间,默认1小时
        private long accessTokenDetect = 3600;
        //token前缀
        private String accessTokenPrefix = "chensino:access_token";
        //登录验证码
        private String smsCodeLoginPrefix = "chensino:login:sms_code";
        //注册验证码
        private String smsCodeRegisterPrefix = "chensino:register:sms_code";
    }
}
