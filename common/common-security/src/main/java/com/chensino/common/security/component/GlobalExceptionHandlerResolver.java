package com.chensino.common.security.component;

import com.chensino.common.core.util.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * @author 204506
 * @version 1.0
 * @Date  2022-07-28 15:50
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
        return ResponseEntity.fail((e instanceof NullPointerException) ? "空指针异常" : e.getLocalizedMessage());
    }

    /**
     * 参数不匹配异常
     *
     * @param e 异常
     * @return 返回统一实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("参数不匹配，请求路径：{} ，exception={}", request.getRequestURI(), e.getMessage(), e);
        return ResponseEntity.fail(e.getLocalizedMessage());
    }

    /**
     * 404
     * @param e 异常
     * @return 返回统一实体
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handlerNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("请求路径{}不存在 ，exception={}", request.getRequestURI(), e.getMessage(), e);
        return ResponseEntity.fail(e.getLocalizedMessage());
    }


    /**
     * 403
     * AccessDeniedException
     * @param e the e
     * @return R
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        String msg = SpringSecurityMessageSource.getAccessor().getMessage("AbstractAccessDecisionManager.accessDenied",
                e.getMessage());
        log.error("拒绝授权异常信息 ex={}", msg, e);
        return ResponseEntity.fail(e.getLocalizedMessage());
    }
}
