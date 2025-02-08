package com.chensino.core.security.filter;

import cn.hutool.core.text.StrPool;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import com.chensino.core.api.entity.CustomSecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private IGlobalCache redisTemplate;

    @Autowired
    public void setRedisTemplate(IGlobalCache redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,@Nonnull FilterChain filterChain) throws ServletException, IOException {
        //1. 解析token
        String token = request.getHeader("X-token");
        //2. token不存在直接放行，后续的FilterInterceptor会校验权限，没有权限依然无法访问接口
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //3. 根据token查询用户信息，目标是设置SecurityContext，若用户随便输入token，则SecurityContext为空，后续的FilterInterceptor会校验权限，没有权限依然无法访问接口
        CustomSecurityUser customSecurityUser = redisTemplate.get(CacheConst.ACCESS_TOKEN_PREFIX + StrPool.COLON + token);
        if (Objects.nonNull(customSecurityUser)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customSecurityUser, customSecurityUser.getPassword(), customSecurityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
