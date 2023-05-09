package com.chensino.core.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 默认不带token访问接口，返回的是403FORBIDDEN，不知道为何这样设计，理论上应该是返回401 UNAUTHORIZED才对，
 * 所以我们这里重写重写一下AuthenticationEntryPoint，当不带token访问让它返回401，之所以这样设计，是因为refresh_token设计
 * 要根据401响应码来进行重新请求新的token
 *
 * 另外，security默认用的Http403ForbiddenEntryPoint
 * @author chenkun
 * @Description
 * @Date 2022/12/6 下午10:25
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Log logger = LogFactory.getLog(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.debug("Pre-authenticated entry point called. Rejecting access");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
