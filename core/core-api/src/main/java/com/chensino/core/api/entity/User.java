package com.chensino.core.api.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 用户实体类
 * @author 204506
 */
@Data
public class User {
    @NotBlank(message = "用户名不能为空")
    private String username;
}
