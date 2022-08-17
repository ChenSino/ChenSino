package com.chensino.core.service.impl;

import com.chensino.core.api.entity.User;
import com.chensino.core.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Administrator
 * @Description User 业务处理层
 * @Date 2022/8/17
 */
@Service
public class UserServiceImpl implements UserService {

    public User getUserById(Long userId){
        User user = new User();
        user.setUsername("tomcat");
        return user;
    }
}
