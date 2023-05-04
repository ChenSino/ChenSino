package com.chensino.core.system.strategy;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.validate.UserLoginDTOValidator;
import com.chensino.core.api.validate.group.PhoneLogin;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

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

    private final UserLoginDTOValidator userLoginDTOValidator;
    /**
     * 手机号验证码登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public LoginUserVO login(UserLoginDTO userLoginDTO) {
        validate(userLoginDTOValidator, PhoneLogin.class,userLoginDTO);
        return  doLogin(new PhoneAuthenticationToken(userLoginDTO.getPhone(), userLoginDTO.getPhoneCode()), authenticationManager, redisTemplate,expiration);
    }
}
