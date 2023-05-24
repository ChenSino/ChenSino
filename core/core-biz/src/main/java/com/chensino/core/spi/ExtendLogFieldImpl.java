package com.chensino.core.spi;

import com.chensino.common.log.spi.ExtendLogField;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author chenkun
 * @Description
 * @date 2023/5/24 下午9:39
 */
public class ExtendLogFieldImpl implements ExtendLogField {
    @Override
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
