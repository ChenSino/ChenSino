package com.chensino.core.system.strategy;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.vo.LoginUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2023/4/28 上午11:04
 * @Created by chenxk
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginByUsernameStrategy implements LoginStrategy {
    private final IGlobalCache redisTemplate;
    private final AuthenticationManager authenticationManager;
    @Value("${token.expiration}")
    private Long expiration;

    /**
     * 手机号验证码登录
     *
     * @param loginUserDTO
     * @return
     */
    @Override
    public LoginUserVO login(LoginUserDTO loginUserDTO) {
        return  doLogin(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()), authenticationManager, redisTemplate,expiration);
    }
}
