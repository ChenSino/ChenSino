package com.chensino.core.security.filter;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.security.component.properties.SecurityProperties;
import com.chensino.core.api.entity.CustomSecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private IGlobalCache redisTemplate;

    private SecurityProperties securityProperties;

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Autowired
    public void setRedisTemplate(IGlobalCache redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 解析token
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        //2. token不存在直接放行，后续的FilterInterceptor会校验权限，没有权限依然无法访问接口
        if (!StringUtils.hasText(bearerToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = bearerToken.split(" ")[1];
        //3. 根据token查询用户信息，目标是设置SecurityContext
        CustomSecurityUser customSecurityUser = redisTemplate.get(securityProperties.getToken().getAccessTokenPrefix() + token);
        if (Objects.nonNull(customSecurityUser)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customSecurityUser.getUsername(), customSecurityUser.getPassword(), customSecurityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            // 4. token续期
            tokenRenewal(token);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * token续签
     *
     * @param token
     */
    private void tokenRenewal(String token) {
        long expire = redisTemplate.getExpire(securityProperties.getToken().getAccessTokenPrefix() + token);
        //若剩余过期时间小于指定时间，就要提前续期
        if (expire < securityProperties.getToken().getAccessTokenDetect()) {
            redisTemplate.expire(securityProperties.getToken().getAccessTokenPrefix() + token, securityProperties.getToken().getAccessTokenExpire());
        }
    }
}
