package com.chensino.common.log.event;

import com.chensino.common.log.entity.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;

/**
 * @author  Administrator
 * @description
 * @createDate  2022/8/17
 */
@Slf4j
public class OperateLogListener{

    @EventListener(OperateLogEvent.class)
    @Async
    public void saveOperateLog(OperateLogEvent operateLogEvent){
       this.validate(operateLogEvent.getOperateLog());
    }


    private void validate(@Valid OperateLog operateLog){
       log.info("校验日日志");
    }
}
