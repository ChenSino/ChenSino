package com.chensino.core.system.factory;

import com.chensino.core.system.strategy.LoginStrategy;

/**
 * @Description TODO
 * @Date 2023/4/28 上午11:09
 * @Created by chenxk
 */
public class LoginStrategyFactory {
    /**
     * 根据类登录型获取登录策略
     * @param type
     * @return
     */
    public static LoginStrategy getLoginStrategy(LoginTypeEnum type) {
        return type.getConstructor().get();
    }
}
