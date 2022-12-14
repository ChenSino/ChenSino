package com.chensino.core.security.filter;

import cn.hutool.core.text.StrPool;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.security.service.CustomUserDetailsService;
import com.chensino.core.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private IGlobalCache redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 解析token
        String token = request.getHeader("X-token");
        //2. token不存在直接放行，后续的FilterInterceptor会校验权限，没有权限依然无法访问接口
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //3. 根据token查询用户信息，目标是设置SecurityContext
        CustomSecurityUser customSecurityUser = redisTemplate.get(CacheConst.TOKEN_PREFIX + StrPool.COLON + token);
        if (Objects.nonNull(customSecurityUser)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customSecurityUser.getUsername(), customSecurityUser.getPassword(), customSecurityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
