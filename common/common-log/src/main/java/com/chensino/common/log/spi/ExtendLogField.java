package com.chensino.common.log.spi;

/**
 * 扩展字段，由调用方提供实现
 * @author chenkun
 * @Description
 * @date 2023/5/24 下午9:33
 */
public interface ExtendLogField {

    /**
     * 不同调用方获取当前登录用户的方式不同，由调用方自行实现
     * @return
     */
    String currentUser();
}