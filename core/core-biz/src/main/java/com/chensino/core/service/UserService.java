package com.chensino.core.service;

import com.chensino.core.api.entity.User;

/**
 * @author Administrator
 */
public interface UserService {

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    User getUserById(Long userId);
}
