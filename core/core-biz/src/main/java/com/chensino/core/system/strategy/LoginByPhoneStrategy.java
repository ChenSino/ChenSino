package com.chensino.core.system.strategy;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Description
 * @Date 2023/4/28 上午11:07
 * @Created by chenxk
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginByPhoneStrategy implements LoginStrategy {
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
        //校验手机号，验证码
        validParams(loginUserDTO);
        return  doLogin(new PhoneAuthenticationToken(loginUserDTO.getPhone(), loginUserDTO.getPhoneCode()), authenticationManager, redisTemplate,expiration);
    }

    private void validParams(LoginUserDTO loginUserDTO) {
        Objects.requireNonNull(loginUserDTO.getPhone(), "手机号不能为空");
        Objects.requireNonNull(loginUserDTO.getPhoneCode(), "验证码不能为空");
    }
}
