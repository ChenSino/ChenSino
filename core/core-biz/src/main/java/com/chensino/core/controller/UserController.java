package com.chensino.core.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "用户管理",tags = {"用户管理"})
public class UserController {

    private final SysUserService sysUserService;
    private  final IGlobalCache globalCache;

    @ApiOperation(value = "根据id查询-value")
    @SysLog("根据用户id查询")
    @GetMapping("/{userId}")
    public ResponseEntity<SysUser> getUserById(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        globalCache.set("user:" + user.getUserName(), user);
        return ResponseEntity.ok(user, "根据id查询用户,username=" + user.getUserName());
    }

    @ApiOperation(value = "查询列表")
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
