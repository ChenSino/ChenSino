package com.chensino.core.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.entity.User;
import com.chensino.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 204506
 * @version 1.0
 * @date 2022-07-28 9:33
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @SysLog("根据用户id查询")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user, "根据id查询用户,username=" + user.getUsername());
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> userList() {
        List<User> userList = new ArrayList<>();
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
    public ResponseEntity<Object> add(@Valid @RequestBody User user) {
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

}
