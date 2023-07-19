package com.chensino.common.log.factory;

import com.chensino.common.log.annotation.SysHistoryLog;
import com.chensino.common.log.strategy.HistoryLogStrategy;
import lombok.experimental.UtilityClass;

/**
 * @author chenxk
 * @description
 * @createDate 2023/6/13 下午5:33
 */
@UtilityClass
public class HistoryLogStrategyFactory {

    /**
     * 根据操作录型获取操作策略
     *
     * @param type
     * @return
     */
    public HistoryLogStrategy getLoginStrategy(SysHistoryLog.OperateType type) {
        return type.getStrategySupplier().get();
    }
}
