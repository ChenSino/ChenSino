package com.chensino.core.system.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrPool;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import com.chensino.core.system.service.LoginService;
import com.chensino.core.system.service.SysUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Data
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    IGlobalCache redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;
    @Value("${token.expiration}")
    private Long expiration;

    @Override
    public ResponseEntity login(SysUser sysUser) {
        //TODO 重复登录逻辑

        //构造一个未认证的对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
        //1. 使用AuthenticationManager认证用户
        Authentication authenticate = null;
        try {

            authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            //2. 认证失败
            log.error("认证失败，{}",e.getMessage());
            throw e;
        }

        //3. 认证通过，生成token,key->token,value->username
        String token = UUID.fastUUID().toString(true);
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authenticate.getPrincipal();
        //4. token存入redis
        redisTemplate.set(CacheConst.TOKEN_PREFIX + StrPool.COLON + token, customSecurityUser, expiration);
        Map<String, Object> data = new HashMap<>(4);
        data.put("access_token", token);
        data.put("authorities", authenticate.getAuthorities());
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity loginByPhone(String phone, String code) {

        //TODO 校验验证码合法性

        //根据手机号和验证码查询用户
        SysUser sysUser =  sysUserService.findUserByPhone(phone);
        //构造一个未认证的对象
        PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());
        //1. 使用AuthenticationManager认证用户
        Authentication authenticate = null;
        try {

            authenticate = authenticationManager.authenticate(phoneAuthenticationToken);
        } catch (Exception e) {
            //2. 认证失败
            log.error("认证失败，{}",e.getMessage());
            throw e;
        }

        //3. 认证通过，生成token,key->token,value->username
        String token = UUID.fastUUID().toString(true);
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authenticate.getPrincipal();
        //4. token存入redis
        redisTemplate.set(CacheConst.TOKEN_PREFIX + StrPool.COLON + token, customSecurityUser, expiration);
        Map<String, Object> data = new HashMap<>(4);
        data.put("access_token", token);
        data.put("authorities", customSecurityUser.getAuthorities());
        return ResponseEntity.ok(data);
    }
}
