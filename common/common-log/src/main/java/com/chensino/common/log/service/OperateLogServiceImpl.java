package com.chensino.common.log.service;

import com.chensino.common.log.entity.OperateLog;
import com.chensino.common.log.mapper.OperateLogMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author chenkun
 * @Date 2023/5/6 下午5:24
 */
@Service
@Data
public class OperateLogServiceImpl implements OperateLogService {

    private final OperateLogMapper operateLogMapper;

    @Override
    public OperateLog queryById(Long id) {
        return operateLogMapper.selectById(id);
    }
}
