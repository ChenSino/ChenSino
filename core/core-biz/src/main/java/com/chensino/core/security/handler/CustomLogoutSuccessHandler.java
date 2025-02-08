package com.chensino.core.security.handler;

import com.chensino.core.api.entity.CustomSecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenkun
 * @description:
 * @date: 2025-02-08 11:53:00
 **/
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomSecurityUser user = (CustomSecurityUser) authentication.getPrincipal();
        String username = user.getUsername();
        log.info("username: {}  is offline now", username);
        responseJsonWriter(response);
    }

    private static void responseJsonWriter(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print("退出成功");
        printWriter.flush();
        printWriter.close();
    }
}
