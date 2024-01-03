package com.chensino.core.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenkun
 * @description: 测试elastic
 * @create: 2024-01-03 10:54:37
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;

}