package com.chensino.common.log.aspect;

import com.chensino.common.log.annotation.SysLog;
import com.chensino.common.log.entity.OperateLog;
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
    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, SysLog sysLog){
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        return null;
    }
}
