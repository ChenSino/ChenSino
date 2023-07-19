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
 * @author chenxk
 * @description
 * @createDate 2023/6/14 上午10:00
 */
@Component
@RequiredArgsConstructor
public class AddHistoryLogStrategy extends HistoryLogStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public HistoryLog setNewObj(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog, HistoryLog historyLog) {
        try {
            //设置newObj
            Object newObj = point.getArgs()[sysHistoryLog.targetParamIndex()];
            historyLog.setNewObj(objectMapper.writeValueAsString(newObj));
        } catch (JsonProcessingException e) {
            throw new BusinessException(e);
        }
        //新增，没有originObj
        return historyLog;
    }
}
