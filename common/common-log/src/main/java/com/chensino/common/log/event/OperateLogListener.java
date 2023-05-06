package com.chensino.common.log.event;

import com.chensino.common.log.mapper.OperateLogMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author  Administrator
 * @description
 * @Date  2022/8/17
 */
@Slf4j
@Component
@Data
public class OperateLogListener{


    private final OperateLogMapper operateLogMapper;

    @EventListener(OperateLogEvent.class)
    @Async
    public void saveOperateLog(OperateLogEvent operateLogEvent){
        operateLogMapper.insert(operateLogEvent.getOperateLog());
    }

}
