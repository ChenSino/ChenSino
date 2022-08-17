package com.chensino.common.log.event;

import com.chensino.common.log.entity.OperateLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Description
 * @Date 2022/8/17
 */
@AllArgsConstructor
@Getter
public class OperateLogEvent {

    private final OperateLog operateLog;
}
