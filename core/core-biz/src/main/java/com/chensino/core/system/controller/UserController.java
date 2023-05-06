package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.extendpoint.PrintApplicationArgs;
import com.chensino.core.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 204506
 * @version 1.0
 * @Date 2022-07-28 9:33
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api(value = "用户管理", tags = {"用户管理"})
public class UserController {

    private final SysUserService sysUserService;
    private final IGlobalCache globalCache;

    private final PrintApplicationArgs printApplicationArgs;

    @ApiOperation(value = "根据id查询-value")
    @SysLog("根据用户id查询")
    @GetMapping("/{userId}")
    @PreAuthorize("@pms.hasPermission('user_query') && @pms.hasPermission('user_update')")
    public ResponseEntity<SysUser> getUserById(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        globalCache.set("user:" + user.getUsername(), user);
        return ResponseEntity.ok(user, "根据id查询用户,username=" + user.getUsername());
    }

    @ApiOperation(value = "查询列表")
    @GetMapping("list")
    public ResponseEntity<List<SysUser>> userList() {
        return ResponseEntity.ok(sysUserService.list());
    }

    @ApiOperation(value = "测试跳过所有过滤器")
    @GetMapping("jumpAllFilterTest")
    public ResponseEntity<String> jumpAllFilterTest() {
        return ResponseEntity.ok("通过webSecurityCustomizer()跳过Security所有过滤器");
    }

    @GetMapping("getSession")
    public String getSession(HttpSession session) {
        return session.toString();
    }

    @SysLog("测试全局异常处理")
    @GetMapping("exception")
    public ResponseEntity<Object> testException() {
        int a = 1 / 0;
        return ResponseEntity.ok(a);
    }


    @SysLog("添加用户")
    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody SysUser user) {
        return ResponseEntity.ok(user);
    }


    @SysLog("获取权限")
    @GetMapping("authentication")
//    @PreAuthorize("@pms.hasRole('ADMIN')")
    public ResponseEntity<Object> getAuthentication() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
}
