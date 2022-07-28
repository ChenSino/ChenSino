package com.chensino.common.core.util;

import com.chensino.common.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @param <T>
 * @author 204506
 * @description 统一响应实体类
 */
@ToString
public class ResponseEntity<T> {
    /**
     * 请求结果，0：成功，1：失败
     */
    @Getter
    @Setter
    private int code;

    /**
     * 响应消息
     */
    @Getter
    @Setter
    private String msg;

    /**
     * 响应数据
     */
    @Getter
    @Setter
    private T data;


    public static <T> ResponseEntity<T> ok() {
        return restResult(null, CommonConstant.SUCCESS_CODE, null);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return restResult(data, CommonConstant.SUCCESS_CODE, null);
    }

    public static <T> ResponseEntity<T> ok(T data, String msg) {
        return restResult(data, CommonConstant.SUCCESS_CODE, msg);
    }

    public static <T> ResponseEntity<T> fail() {
        return restResult(null, CommonConstant.FAIL_CODE, null);
    }

    public static <T> ResponseEntity<T> fail(String msg) {
        return restResult(null, CommonConstant.FAIL_CODE, msg);
    }

    public static <T> ResponseEntity<T> fail(T data) {
        return restResult(data, CommonConstant.FAIL_CODE, null);
    }

    public static <T> ResponseEntity<T> fail(T data, String msg) {
        return restResult(data, CommonConstant.FAIL_CODE, msg);
    }

    public static <T> ResponseEntity<T> restResult(T data, int code, String msg) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(code);
        responseEntity.setData(data);
        responseEntity.setMsg(msg);
        return responseEntity;
    }
}
