package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.properties.GithubProperties;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.system.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chenkun
 */
@Controller
@Data
@Tag(name = "系统用户接口", description = "系统用户接口")
public class LoginController {

    private final LoginService loginService;
    private final GithubProperties githubProperties;
    private final ObjectMapper objectMapper;

    @PostMapping("login")
    @ResponseBody
    @SysLog("登录接口")
    @Operation(summary = "登录接口", description = "登录接口")
    public ResponseEntity<LoginUserVO> login(@RequestBody @Validated  UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(loginService.login(userLoginDTO));
    }

    /**
     * 前端github的oauth请求，也可以前端直接请求github的授权地址
     * @param response
     * @throws IOException
     */
    @GetMapping("login/oauth2/github")
    @Operation(summary = "github授权页面", description = "github授权页面")
    public void githubLogin( HttpServletResponse response) throws IOException {
        loginService.githubRedirect(response);
    }

    @GetMapping("login/oauth2/code/github")
    @Operation(summary = "github登录成功的回调", description = "github登录成功的回调")
    @SneakyThrows
    public ModelAndView githubCallback(@RequestParam  String code) {
        ModelAndView modelAndView = new ModelAndView("github");
        LoginUserVO loginUserVO = loginService.githubCallback(code);
        //通过BASE64解决前端 window.opener.postMessage('${loginUser}', `${domain}`)传递引号产生的解析问题，前端使用时base64反解码
        String loginVoJsonBase64 = Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(loginUserVO).getBytes(StandardCharsets.UTF_8));
        modelAndView.addObject("loginUserVO", loginVoJsonBase64);
        modelAndView.addObject("domain", githubProperties.getFrontendHost());
        return modelAndView;
    }

    /**
     *注意默认情况下http://host:ip/logout是security的一个端点，会在security过滤器中处理
        如果想让http://localhost:8888/logout进入自己的controller,需要在security设置自定义logoutUrl
        @see  {@link com.chensino.core.security.config.SecurityConfig}
     * @param bearerToken
     * @return
     */
    @GetMapping("logout")
    @ResponseBody
    @SysLog("登出")
    @Operation(summary = "登出接口", description = "登出接口")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        loginService.logout(bearerToken);
        return ResponseEntity.ok();
    }

}
