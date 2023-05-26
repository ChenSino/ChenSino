package com.chensino.core.system.strategy;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.security.component.properties.SecurityProperties;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.validate.UserLoginDTOValidator;
import com.chensino.core.api.validate.group.GithubLogin;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.security.token.GithubAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Date 2023/4/28 上午11:07
 * @Created by chenxk
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginByGithubStrategy implements LoginStrategy {
    private final IGlobalCache redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final UserLoginDTOValidator userLoginDTOValidator;
    /**
     * 手机号验证码登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public LoginUserVO login(UserLoginDTO userLoginDTO) {
        validate(userLoginDTOValidator, GithubLogin.class,userLoginDTO);
        return  doLogin(new GithubAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()), authenticationManager, redisTemplate,securityProperties);
    }
}
