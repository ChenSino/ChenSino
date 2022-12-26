package com.chensino.core;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.stereotype.Component;

/**
 * 自定义springboot启动失败异常捕获端点
 */
@Component
public class MyFailureAnalyzers extends AbstractFailureAnalyzer {
    /**
     *
     * @param rootFailure
     * @param cause
     * @return 返回空代表自定义的方法也无法处理当前 异常，继续抛出
     */
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Throwable cause) {
        System.out.println("自定义springboot启动失败处理逻辑。。。。。");
        System.out.println(rootFailure.getMessage());
        System.out.println(cause.getMessage());
        return null;
    }
}
