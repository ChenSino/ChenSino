package com.chensino.core.security.provider;

import cn.hutool.core.text.StrPool;
import com.chensino.common.core.exception.VerificationCodeException;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import com.chensino.core.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author chenkun
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PhoneAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final SysUserService sysUserService;
    private final IGlobalCache redisTemplate;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("enter into custom AuthenticationProvider");
        // 校验手机号/验证码是否匹配
        String phone = authentication.getPrincipal().toString();
        String smsCode = redisTemplate.get(CacheConst.SMS_CODE_LOGIN_PREFIX + StrPool.COLON + phone);
        if (smsCode == null) {
            throw new VerificationCodeException("请先发送验证码");
        }
        if (!Objects.equals(smsCode, authentication.getCredentials().toString())) {
            throw new VerificationCodeException("验证码错误");
        }
        // 根据手机号查询username
        SysUser user = Optional.ofNullable(sysUserService.findUserByPhone(phone)).orElseThrow(() -> new RuntimeException("手机号未绑定用户"));
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) userDetailsService.loadUserByUsername(user.getUsername());
        return new PhoneAuthenticationToken(customSecurityUser, smsCode, customSecurityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
