package com.chensino.core.security.provider;

import cn.hutool.core.util.StrUtil;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.security.token.GithubAuthenticationToken;
import com.chensino.core.security.token.OAuthAuthenticationToken;
import com.chensino.core.security.token.PhoneAuthenticationToken;
import com.chensino.core.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GithubAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("enter into custom GithubAuthenticationProvider");
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        return new GithubAuthenticationToken(customSecurityUser, customSecurityUser.getPassword(),customSecurityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GithubAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
