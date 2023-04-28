package com.chensino.core.system.strategy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.security.token.PhoneAuthenticationToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Description 登录策略
 * @Date 2023/4/28 上午10:57
 * @Created by chenxk
 */
public interface LoginStrategy {

    Logger log = LoggerFactory.getLogger(LoginStrategy.class);
    /**
     * 登录用户返回给定数据到前端
     *
     * @param loginUserDTO
     * @return
     */
    LoginUserVO login(LoginUserDTO loginUserDTO);

    default LoginUserVO doLogin(Authentication authentication, AuthenticationManager authenticationManager, IGlobalCache redisTemplate, long expiration) {
        //1. 使用AuthenticationManager认证用户
        Authentication authenticate;
        try {

            authenticate = authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            //2. 认证失败
            log.error("认证失败，{}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        //3. 认证通过，生成token,key->token,value->username
        String token = UUID.fastUUID().toString(true);
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authenticate.getPrincipal();
        //4. token存入redis
        redisTemplate.set(CacheConst.ACCESS_TOKEN_PREFIX + StrPool.COLON + token, customSecurityUser, expiration);

        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setToken(token);
        if (CollectionUtil.isNotEmpty(authorities)) {
            loginUserVO.setAuthorities(authorities.stream().map(auth -> auth.getAuthority().toString()).collect(Collectors.toList()));
        }
        //手机验证码登录成功要马上清除验证码
        if (authentication instanceof PhoneAuthenticationToken ) {
            redisTemplate.del(CacheConst.SMS_CODE_LOGIN_PREFIX + StrUtil.COLON + authentication.getPrincipal());
        }
        return loginUserVO;
    }

}
