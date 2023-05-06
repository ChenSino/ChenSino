package com.chensino.common.core.exception;

/**
 * @author chenkun
 * @createDate 2023/5/6 下午3:01
 */
public class BadParameterException extends RuntimeException{
    public BadParameterException() {
    }

    public BadParameterException(String message) {
        super(message);
    }

    public BadParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadParameterException(Throwable cause) {
        super(cause);
    }

    public BadParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
