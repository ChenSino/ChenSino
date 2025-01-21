package com.chensino.core.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.properties.GithubProperties;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.system.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
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
@RequestMapping("login")
@Data
@Tag(name = "登录接口", description = "登录接口")
public class LoginController {

    private final LoginService loginService;
    private final GithubProperties githubProperties;

    @PostMapping
    @ResponseBody
//    @SysLog("登录接口")
    @Operation(summary = "登录接口", description = "登录接口")
    public ResponseEntity<LoginUserVO> login(@RequestBody @Validated  UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(loginService.login(userLoginDTO));
    }

    /**
     * 前端github的oauth请求，也可以前端直接请求github的授权地址
     * @param response
     * @throws IOException
     */
    @GetMapping("/oauth2/github")
    @Operation(summary = "github授权页面", description = "github授权页面")
    public void githubLogin(HttpServletResponse response) throws IOException {
        loginService.githubRedirect(response);
    }

    @GetMapping("/oauth2/code/github")
    @Operation(summary = "github登录成功的回调", description = "github登录成功的回调")
    public ModelAndView githubCallback(@RequestParam  String code) {
        ModelAndView modelAndView = new ModelAndView("github");
        LoginUserVO loginUserVO = loginService.githubCallback(code);
        //通过BASE64解决前端 window.opener.postMessage('${loginUser}', `${domain}`)传递引号产生的解析问题，前端使用时base64反解码
        String loginVoJsonBase64 = Base64.getEncoder().encodeToString(JSONObject.toJSONString(loginUserVO).getBytes(StandardCharsets.UTF_8));
        modelAndView.addObject("loginUserVO", loginVoJsonBase64);
        modelAndView.addObject("domain", githubProperties.getFrontendHost());
        return modelAndView;
    }
}
