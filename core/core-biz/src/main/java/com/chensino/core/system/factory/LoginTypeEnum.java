package com.chensino.core.system.factory;

import cn.hutool.extra.spring.SpringUtil;
import com.chensino.core.system.strategy.LoginByPhoneStrategy;
import com.chensino.core.system.strategy.LoginByUsernameStrategy;
import com.chensino.core.system.strategy.LoginStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * @Description TODO
 * @Date 2023/4/28 上午11:20
 * @Created by chenxk
 */
@RequiredArgsConstructor
@Getter
public enum LoginTypeEnum {
    //lambada表达式，实际传入的是Supplier类型，重写的get方法，使用idea展开lambda就很明显了
    PHONE(() -> SpringUtil.getBean(LoginByPhoneStrategy.class)),
    USERNAME(() -> SpringUtil.getBean(LoginByUsernameStrategy.class));

    private final Supplier<LoginStrategy> constructor;
}
