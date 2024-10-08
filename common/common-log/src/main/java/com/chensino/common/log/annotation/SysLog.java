package com.chensino.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解，启用则记录日志
 *
 * @author 204506
 * @version 1.0
 * @Date 2022-07-28 18:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SysLog {

    /**
     * 日志描述
     * @return {String}
     */
    String value();
}
