package com.chensino.core.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.service.SysUserService;
import com.chensino.core.api.entity.SysUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 204506
 * @version 1.0
 * @createDate  2022-07-28 9:33
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @SysLog("根据用户id查询")
    @GetMapping("/{userId}")
    public ResponseEntity<SysUser> getUserById(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        return ResponseEntity.ok(user, "根据id查询用户,username=" + user.getUserName());
    }

    @GetMapping("list")
    public ResponseEntity<List<SysUser>> userList() {
        List<SysUser> userList = new ArrayList<>();
        return ResponseEntity.ok(userList);
    }

    @SysLog("测试全局异常处理")
    @GetMapping("exception")
    public ResponseEntity<Object> testException() {
        int a = 1/0;
        return ResponseEntity.ok(a);
    }

    @SysLog("添加用户")
    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody SysUser user) {
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

}
