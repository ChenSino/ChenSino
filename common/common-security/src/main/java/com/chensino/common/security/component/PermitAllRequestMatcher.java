package com.chensino.common.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.chensino.common.security.component.annotation.OpenApi;
import com.chensino.common.security.component.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 接口放行白名单
 * @author chenkun
 * @Date 2023/5/6 下午4:00
 */
@Configuration
public class PermitAllRequestMatcher implements RequestMatcher {
    private SecurityProperties securityProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean matches(HttpServletRequest request) {
        //1. 放行配置的白名单
        String requestURI = request.getRequestURI();
        if (CollUtil.isNotEmpty(securityProperties.getWhiteList())) {
            for (String whiteUrl : securityProperties.getWhiteList()) {
                if (antPathMatcher.match(whiteUrl, requestURI)) {
                    return true;
                }
            }
        }
        //2. 放行OpenApi注解的接口
        HandlerMethod handlerMethod = getHandlerMethod(request);
        if (handlerMethod == null) {
            return false;
        }
        return handlerMethod.hasMethodAnnotation(OpenApi.class);
    }

    private HandlerMethod getHandlerMethod(HttpServletRequest request) {
        HandlerExecutionChain handler;
        try {
            //此处必须按照名字获取bean使用HandlerMapping类型获取，会有多个bean导致报错
            HandlerMapping handlerMapping = SpringUtil.getBean("requestMappingHandlerMapping");
            handler = handlerMapping.getHandler(request);
        } catch (Exception e) {
            return null;
        }

        if (handler == null) {
            return null;
        }
        Object handlerObject = handler.getHandler();
        if (!(handlerObject instanceof HandlerMethod)) {
            return null;
        }
        return (HandlerMethod) handlerObject;
    }

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
