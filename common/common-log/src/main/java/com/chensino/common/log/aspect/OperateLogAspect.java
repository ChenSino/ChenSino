package com.chensino.common.log.aspect;

import com.chensino.common.log.annotation.OperateLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 日志切面
 *
 * @author 204506
 * @version 1.0
 * @date 2022-07-28 18:25
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class OperateLogAspect {

    private final ApplicationEventPublisher publisher;
    @Around("@annotation(operateLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point,OperateLog operateLog){
        String logMsg = operateLog.value();
        log.info("方法执行前置，{}",logMsg);
        Object obj = point.proceed();
        log.info("后置日志");
        return obj;
    }
}
