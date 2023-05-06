package com.chensino.common.log.service;

import com.chensino.common.log.entity.OperateLog;

/**
 * @author chenkun
 * @Date 2023/5/6 下午5:24
 */
public interface OperateLogService {
    OperateLog queryById(Long id);
}
