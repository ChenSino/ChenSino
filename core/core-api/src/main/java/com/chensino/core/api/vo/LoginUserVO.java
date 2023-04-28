package com.chensino.core.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description 响应给前端的数据
 * @Date 2023/4/28 上午11:00
 * @Created by chenxk
 */
@Data
public class LoginUserVO {

    private String token;

    private List<String> authorities;
}
