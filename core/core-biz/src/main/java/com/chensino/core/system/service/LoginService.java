package com.chensino.core.system.service;

import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.vo.LoginUserVO;

public interface LoginService {
    LoginUserVO login(LoginUserDTO loginUserDTO);
}
