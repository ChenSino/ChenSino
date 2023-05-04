package com.chensino.common.core.exception;

/**
 * @Description 验证码异常
 * @Date 2023/5/5 上午9:52
 * @Author chenkun
 */
public class VerificationCodeException extends RuntimeException{

    public VerificationCodeException() {
        super();
    }

    public VerificationCodeException(String message) {
        super(message);
    }
}
