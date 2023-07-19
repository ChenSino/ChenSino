package com.chensino.core.spi;

import cn.hutool.extra.spring.SpringUtil;
import com.chensino.common.core.exception.BusinessException;
import com.chensino.common.log.spi.ExtendLogField;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

/**
 * @author chenkun
 * @Description
 * @date 2023/5/24 下午9:39
 */
public class ExtendLogFieldImpl implements ExtendLogField {
    @Override
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public String originObj(String sql) {
        SqlSession sqlSession = SpringUtil.getBean(SqlSession.class);
        Map<String, Object> resultList = sqlSession.selectOne("com.chensino.core.system.mapper.PlainSqlMapper.selectOne", sql);
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        String s;
        try {
            s = objectMapper.writeValueAsString(resultList);
        } catch (JsonProcessingException e) {
            throw new BusinessException(e.getMessage());
        }
        return s;
    }
}
