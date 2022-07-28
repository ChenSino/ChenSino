package com.chensino.core.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.core.api.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 204506
 * @version 1.0
 * @date 2022-07-28 9:33
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable Integer userId) {
        User user = new User();
        user.setUsername("吴彦祖");
        return ResponseEntity.ok(user, "根据id查询用户");
    }

    @GetMapping("list")
    public ResponseEntity userList() {
        List<User> userList = new ArrayList<>();
        return ResponseEntity.ok(userList);
    }

}
