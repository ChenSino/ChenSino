package com.chensino.core.system.service.impl;

import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.system.factory.LoginStrategyFactory;
import com.chensino.core.system.factory.LoginTypeEnum;
import com.chensino.core.system.service.LoginService;
import com.chensino.core.system.service.SysUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final IGlobalCache redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final SysUserService sysUserService;
    @Value("${token.expiration}")
    private Long expiration;

    @Override
    public LoginUserVO login(LoginUserDTO loginUserDTO) {
        return LoginStrategyFactory.getLoginStrategy(LoginTypeEnum.valueOf(loginUserDTO.getLoginType())).login(loginUserDTO);
    }

}
