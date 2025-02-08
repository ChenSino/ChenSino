package com.chensino.core.security.handler;

import cn.hutool.core.text.StrPool;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.data.configuration.constant.CacheConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * @author chenkun
 * @description: 自定义退出登录
 * @date: 2025-02-08 11:50:40
 **/
@Data
@Component
public class CustomLogoutHandler implements LogoutHandler {
    private final IGlobalCache redisTemplate;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("X-token");
        redisTemplate.del(CacheConst.ACCESS_TOKEN_PREFIX + StrPool.COLON + token);
    }
}
