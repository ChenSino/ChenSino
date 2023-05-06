package com.chensino.common.security.component.annotation;

import java.lang.annotation.*;

/**
 * @author chenkun
 * @createDate 2023/5/6 下午3:57
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenApi {
}
