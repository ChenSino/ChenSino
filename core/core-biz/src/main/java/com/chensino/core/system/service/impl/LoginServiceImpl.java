package com.chensino.core.system.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.security.component.properties.SecurityProperties;
import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.entity.SysUserThird;
import com.chensino.core.api.properties.GithubProperties;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.system.factory.LoginStrategyFactory;
import com.chensino.core.system.factory.LoginTypeEnum;
import com.chensino.core.system.mapper.SysUserThirdMapper;
import com.chensino.core.system.service.LoginService;
import com.chensino.core.system.service.SysUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final IGlobalCache redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final SysUserService sysUserService;
    private final SysUserThirdMapper sysUserThirdMapper;
    private final GithubProperties githubProperties;
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;


    @Override
    public LoginUserVO login(UserLoginDTO userLoginDTO) {
        return LoginStrategyFactory.getLoginStrategy(LoginTypeEnum.valueOf(userLoginDTO.getLoginType())).login(userLoginDTO);
    }

    @Override
    @SneakyThrows
    public LoginUserVO githubCallback(String code) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("code", code);
        params.put("client_id", githubProperties.getClientId());
        params.put("client_secret", githubProperties.getSecret());
        //根据code请求github的token
        String response = HttpUtil.post(githubProperties.getAccessTokenUrl(), params);
        String token = response.split("&")[0].split("=")[1];
        // 拿到github token，根据token请求用户信息
        String githubUser = HttpRequest.get(githubProperties.getUserInfoUrl()).header("Authorization", "Bearer " + token).execute().body();
        //根据github用户查询对应本地用户
        JsonNode jsonNode = objectMapper.readTree(githubUser);
        String githubUsername =  jsonNode.get("name").textValue();
        SysUserThird sysUserThird = sysUserThirdMapper.selectByUsername(githubUsername);
        //构造一个登录对象免密码登录
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginType("GITHUB");
        userLoginDTO.setUsername(sysUserThird.getUsername());
        return this.login(userLoginDTO);
    }

    @Override
    public void githubRedirect(HttpServletResponse response) throws IOException {
        StringBuilder url = new StringBuilder();
        url.append(githubProperties.getAuthorizeUrl())
                .append("?client_id=")
                .append(githubProperties.getClientId())
                .append("&redirect_uri=")
                .append(githubProperties.getRedirectUri());
        response.sendRedirect(url.toString());
    }

    @Override
    public void logout(String bearerToken) {
        redisTemplate.del(securityProperties.getToken().getAccessTokenPrefix() + bearerToken.split(" ")[1]);
    }
}
