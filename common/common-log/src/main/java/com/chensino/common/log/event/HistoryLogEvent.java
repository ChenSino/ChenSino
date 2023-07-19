package com.chensino.common.log.event;

import com.chensino.common.log.entity.HistoryLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Description
 * @Date 2022/8/17
 */
@AllArgsConstructor
@Getter
public class HistoryLogEvent {

    private final HistoryLog historyLog;
}
