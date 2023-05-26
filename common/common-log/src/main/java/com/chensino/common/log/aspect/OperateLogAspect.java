package com.chensino.common.log.aspect;

import cn.hutool.core.text.StrPool;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.common.log.entity.OperateLog;
import com.chensino.common.log.event.OperateLogEvent;
import com.chensino.common.log.util.LogTypeEnum;
import com.chensino.common.log.util.SysLogUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author 204506
 * @version 1.0
 * @Date 2022-07-28 18:25
 */
@Slf4j
@Aspect
@AllArgsConstructor
@Component
public class OperateLogAspect {

    private final ApplicationEventPublisher publisher;

    private final ObjectMapper objectMapper;

    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        OperateLog operateLog = SysLogUtils.getSysLog();

        Object[] args = point.getArgs();
        StringBuilder params = new StringBuilder();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params.append("参数");
                params.append(i + 1);
                params.append(StrPool.COLON);
                params.append(objectMapper.writeValueAsString(args[i]));
                params.append(StrPool.LF);
            }
        }
        Long startTime = System.currentTimeMillis();
        Object obj;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            operateLog.setType(LogTypeEnum.ERROR.getType());
            operateLog.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            operateLog.setTime(endTime - startTime);
            operateLog.setParams(params.toString());
            operateLog.setMethod(strMethodName);
            operateLog.setClassName(strClassName);
            operateLog.setTitle(sysLog.value());
            publisher.publishEvent(new OperateLogEvent(operateLog));
        }
        return obj;
    }
}
