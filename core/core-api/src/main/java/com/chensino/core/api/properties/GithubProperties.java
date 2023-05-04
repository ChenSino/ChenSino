package com.chensino.core.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2023/5/4 下午5:39
 * @Created by chenxk
 */
@ConfigurationProperties(prefix = "oauth.github")
@Data
@Configuration
public class GithubProperties {
    //在github注册的客户端id
    private String clientId;
    //授权后重定向的地址
    private String redirectUri;
    //在github注册的应用secret
    private String secret;
    //请求登录的地址
    private String authorizeUrl;
    //重定向后请求github获取token的地址
    private String accessTokenUrl;
    //获取github用户信息的url
    private String userInfoUrl;
    //前端的域
    private String frontendHost;
}
