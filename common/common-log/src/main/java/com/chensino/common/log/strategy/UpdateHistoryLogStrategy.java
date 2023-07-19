package com.chensino.common.log.strategy;

import com.chensino.common.core.exception.BusinessException;
import com.chensino.common.log.annotation.SysHistoryLog;
import com.chensino.common.log.entity.HistoryLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 更新数据
 *
 * @author chenxk
 * @description
 * @createDate 2023/6/13 下午5:39
 */
@Component
@RequiredArgsConstructor
public class UpdateHistoryLogStrategy extends HistoryLogStrategy {

    private final  ObjectMapper objectMapper;
    @Override
    public HistoryLog setNewObj(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog, HistoryLog historyLog) {
        //设置修改后对象
        Object newObj = point.getArgs()[sysHistoryLog.targetParamIndex()];
        try {
            historyLog.setNewObj(objectMapper.writeValueAsString(newObj));
        } catch (JsonProcessingException e) {
            throw new BusinessException(e);
        }
        return historyLog;
    }


}
