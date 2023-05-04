package com.chensino.core.system.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrPool;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.validate.UserLoginDTOValidator;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindException;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author chenkun
 * @Description 登录策略
 * @Date 2023/4/28 上午10:57
 */
public interface LoginStrategy {

    Logger log = LoggerFactory.getLogger(LoginStrategy.class);

    /**
     * 登录用户返回给定数据到前端
     *
     * @param userLoginDTO
     * @return
     */
    LoginUserVO login(UserLoginDTO userLoginDTO);

    /**
     * 登录
     *
     * @param authentication
     * @param authenticationManager
     * @param redisTemplate
     * @param expiration
     * @return
     */
    default LoginUserVO doLogin(Authentication authentication, AuthenticationManager authenticationManager, IGlobalCache redisTemplate, long expiration) {
        //1. 使用AuthenticationManager认证用户
        Authentication authenticate;
        authenticate = authenticationManager.authenticate(authentication);
        //3. 认证通过，生成token,key->token,value->username
        String token = UUID.fastUUID().toString(true);
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authenticate.getPrincipal();
        //4. token存入redis
        redisTemplate.set(CacheConst.ACCESS_TOKEN_PREFIX + StrPool.COLON + token, customSecurityUser, expiration);

        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setToken(token);
        if (CollUtil.isNotEmpty(authorities)) {
            loginUserVO.setAuthorities(authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.toList()));
        }
        //手机验证码登录成功要马上清除验证码
        if (authentication instanceof PhoneAuthenticationToken) {
            redisTemplate.del(CacheConst.SMS_CODE_LOGIN_PREFIX + StrPool.COLON + authentication.getPrincipal());
        }
        return loginUserVO;
    }


    /**
     * 校验参数
     *
     * @param userLoginDTOValidator
     * @param clazz
     * @param userLoginDTO
     */
    default void validate(UserLoginDTOValidator userLoginDTOValidator, Class<?> clazz, UserLoginDTO userLoginDTO) {
        BindException errors = new BindException(userLoginDTO, "userLoginDTO");
        userLoginDTOValidator.validate(userLoginDTO, errors, clazz);
        // 判断校验是否成功
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
