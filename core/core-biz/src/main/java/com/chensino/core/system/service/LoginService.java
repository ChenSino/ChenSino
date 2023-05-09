package com.chensino.core.system.service;

import com.chensino.core.api.dto.UserLoginDTO;
import com.chensino.core.api.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface LoginService {
    LoginUserVO login(UserLoginDTO userLoginDTO);

    LoginUserVO githubCallback(String code);

    void githubRedirect(HttpServletResponse response) throws IOException;
}
