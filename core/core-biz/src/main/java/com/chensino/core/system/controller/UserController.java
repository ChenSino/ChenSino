package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "用户管理", description = "用户管理")
public class UserController {

    private final SysUserService sysUserService;

    @SysLog("根据用户id查询")
    @GetMapping("/{userId}")
    @PreAuthorize("@pms.hasPermission('user_query') && @pms.hasPermission('user_update')")
    @Operation(summary = "根据id查询", description = "根据id查询")
    @Parameter(name = "userId", description = "用户id", required = true)
    public ResponseEntity<SysUser> getUserById(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        return ResponseEntity.ok(user, "根据id查询用户,username=" + user.getUsername());
    }

    @GetMapping("list")
    @Operation(summary = "查询列表", description = "查询列表")
    public ResponseEntity<List<SysUser>> userList() {
        return ResponseEntity.ok(sysUserService.list());
    }


    @SysLog("添加用户")
    @PostMapping
    @Operation(summary = "添加用户", description = "添加用户")
    public ResponseEntity<Object> add(@Valid @RequestBody SysUser user) {
        return ResponseEntity.ok(sysUserService.save(user));
    }

    @SysLog("修改用户")
    @PutMapping
    @Operation(summary = "修改用户", description = "修改用户")
    public ResponseEntity<Object> update(@Valid @RequestBody SysUser user) {
        return ResponseEntity.ok(sysUserService.saveOrUpdate(user));
    }

}
