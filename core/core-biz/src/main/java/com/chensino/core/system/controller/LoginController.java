package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.core.api.dto.LoginUserDTO;
import com.chensino.core.api.vo.LoginUserVO;
import com.chensino.core.system.service.LoginService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@Data
public class LoginController {

    private final LoginService loginService;
    @PostMapping
    public ResponseEntity login(@RequestBody @Validated LoginUserDTO loginUserDTO){
        return ResponseEntity.ok(loginService.login(loginUserDTO));
    }
}
