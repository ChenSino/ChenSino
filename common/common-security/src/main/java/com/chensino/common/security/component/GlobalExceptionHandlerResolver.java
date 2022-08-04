package com.chensino.common.security.component;

import com.chensino.common.core.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 204506
 * @version 1.0
 * @date 2022-07-28 15:50
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerResolver {

    /**
     * 全局异常
     *
     * @param e 异常
     * @return 返回统一实体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerGlobalException(Exception e) {
        log.error("全局异常信息 exception={}", e.getMessage(), e);
        return ResponseEntity.fail(e.getLocalizedMessage());
    }

    /**
     * 全局异常
     *
     * @param e 异常
     * @return 返回统一实体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("参数不匹配，请求路径：{} ，exception={}", requestURI, e.getMessage(), e);
        return ResponseEntity.fail(e.getLocalizedMessage());
    }
}