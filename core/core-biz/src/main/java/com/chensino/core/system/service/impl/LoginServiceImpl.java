package com.chensino.core.system.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.system.service.LoginService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
public class LoginServiceImpl implements LoginService {
    @Autowired
    IGlobalCache redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${token.expiration}")
    private Long expiration;

    @Override
    public ResponseEntity login(SysUser sysUser) {
        //构造一个未认证的对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(), sysUser.getPassword());
        //1. 使用AuthenticationManager认证用户
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //2. 认证不通过
        if (!Objects.nonNull(authenticate)) {
            throw new RuntimeException();
        }
        //3. 认证通过，生成token,key->token,value->username
        String token = UUID.fastUUID().toString(true);
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) authenticate.getPrincipal();
        //4. token存入redis
        redisTemplate.set(CacheConst.TOKEN_PREFIX + StrPool.COLON + token,customSecurityUser,expiration);
        return ResponseEntity.ok(token);
    }
}
