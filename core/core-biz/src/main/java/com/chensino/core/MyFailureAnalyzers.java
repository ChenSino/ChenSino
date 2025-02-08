package com.chensino.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.stereotype.Component;

/**
 * 自定义springboot启动失败异常捕获端点
 */
@Component
@Slf4j
public class MyFailureAnalyzers extends AbstractFailureAnalyzer<Exception> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Exception cause) {
        log.error("自定义springboot启动失败处理逻辑,异常：{}", cause.getMessage());
        return null;
    }
}
