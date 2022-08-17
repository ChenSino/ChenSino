package com.chensino.common.log.event;

import com.chensino.common.log.entity.OperateLog;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;

/**
 * @Author Administrator
 * @Description
 * @Date 2022/8/17
 */
public class OperateLogListener{

    @EventListener(OperateLogEvent.class)
    @Async
    public void saveOperateLog(OperateLogEvent operateLogEvent){
       this.vilidate1(operateLogEvent.getOperateLog());
    }


    private void vilidate1(@Valid OperateLog operateLog){
        System.out.println(111);
    }
}
