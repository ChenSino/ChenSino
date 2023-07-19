package com.chensino.common.log.service.impl;

import com.chensino.common.log.entity.HistoryLog;
import com.chensino.common.log.mapper.HistoryLogMapper;
import com.chensino.common.log.service.HistoryLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chenxk
 * @description
 * @createDate 2023/6/13 上午11:03
 */
@Service
@RequiredArgsConstructor
public class HistoryLogServiceImpl implements HistoryLogService {

    private final HistoryLogMapper historyLogMapper;

    public int insert(HistoryLog historyLog) {
        return historyLogMapper.insert(historyLog);
    }
}
