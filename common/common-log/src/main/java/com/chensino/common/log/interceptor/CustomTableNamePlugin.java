package com.chensino.common.log.interceptor;

import com.chensino.common.log.configuration.CustomLogProperties;
import lombok.Data;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * @author chenkun
 * @Description
 * @date 2023/5/23 下午9:46
 */
@Intercepts(
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
)
@Data
@Component
public class CustomTableNamePlugin implements Interceptor {

    private final CustomLogProperties customLogProperties;
    private static final String DEFAULT_LOG_TABLE_NAME = "t_operate_log";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        sql = sql.replace(DEFAULT_LOG_TABLE_NAME, customLogProperties.getLogTableName());
        metaObject.setValue("delegate.boundSql.sql",sql);
        System.out.println(metaObject.getValue("delegate.boundSql.sql"));
        return invocation.proceed();
    }
}
