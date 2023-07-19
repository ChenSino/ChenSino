package com.chensino.core.system.mapper;

import java.util.LinkedHashMap;

/**
 * @author chenxk
 * @description
 * @createDate 2023/6/13 下午3:31
 */
public interface PlainSqlMapper {

    LinkedHashMap<String, Object> selectOne(String sql);
}
