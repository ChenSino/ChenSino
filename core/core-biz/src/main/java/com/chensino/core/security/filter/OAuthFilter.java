package com.chensino.core.security.filter;

import com.chensino.core.security.token.OAuthAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@AllArgsConstructor
public class OAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 获取从OAuth授权服务器得到authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, "Oauth授权服务器响应数据有误！");
        //2. 拿到用户名
        String username = authentication.getName();
        Assert.notNull(username, "Oauth授权服务器响应数据有误，用户名不能为空");
        //3. 根据用户名查询本系统的用户权限信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //4. 根据用户权限信息生成token
        OAuthAuthenticationToken oAuthAuthenticationToken = new OAuthAuthenticationToken(username, userDetails.getPassword());
        oAuthAuthenticationToken.setDetails(userDetails);
        //5. 把token设置到Security上下文
        SecurityContextHolder.getContext().setAuthentication(oAuthAuthenticationToken);
        filterChain.doFilter(request, response);
    }

}
