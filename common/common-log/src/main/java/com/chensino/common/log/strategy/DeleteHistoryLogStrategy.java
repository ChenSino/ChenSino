package com.chensino.common.log.strategy;

import com.chensino.common.log.annotation.SysHistoryLog;
import com.chensino.common.log.entity.HistoryLog;
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
public class DeleteHistoryLogStrategy extends HistoryLogStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public HistoryLog setNewObj(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog, HistoryLog historyLog) {
        //设置originObj
        return historyLog;
    }
}
