package com.chensino.core.system.service;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.api.vo.UserVO;

public interface LoginService {

    ResponseEntity login(SysUser user);

    ResponseEntity loginByPhone(String phone, String code);

}
