package com.chensino.common.log.util;

import com.chensino.common.log.entity.HistoryLog;
import com.chensino.common.log.spi.ExtendLogField;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ServiceLoader;

/**
 * @author chenxk
 * @description
 * @createDate 2023/6/13 下午5:20
 */
@UtilityClass
public class HistoryLogUtil {
    public HistoryLog getHistoryLog() {

        HistoryLog historyLog = new HistoryLog();
        historyLog.setCreateTime(LocalDateTime.now());
        //设置createBy字段，若调用方没提供SPI则createBy为null
        ServiceLoader<ExtendLogField> serviceLoader = ServiceLoader.load(ExtendLogField.class);
        serviceLoader.findFirst().ifPresent(extendLogField -> historyLog.setCreateBy(extendLogField.currentUser()));
        return historyLog;
    }

}
