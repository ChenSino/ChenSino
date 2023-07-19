package com.chensino.common.log.strategy;

import cn.hutool.core.text.StrPool;
import com.chensino.common.core.exception.BusinessException;
import com.chensino.common.log.annotation.SysHistoryLog;
import com.chensino.common.log.entity.HistoryLog;
import com.chensino.common.log.spi.ExtendLogField;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ServiceLoader;

/**
 * @author chenxk
 * @description
 * @createDate 2023/6/13 下午5:34
 */
@Component
public abstract class HistoryLogStrategy {


    /**
     * @param point
     * @param sysHistoryLog
     * @return
     */
    public HistoryLog getHistoryLog(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog) {
        HistoryLog historyLog = new HistoryLog();
        //设置创建时间
        historyLog.setCreateTime(LocalDateTime.now());
        //设置创建人，若调用方没提供SPI则createBy为null
        ServiceLoader<ExtendLogField> serviceLoader = ServiceLoader.load(ExtendLogField.class);
        serviceLoader.findFirst().ifPresent(extendLogField -> historyLog.setCreateBy(extendLogField.currentUser()));
        //设置业务类型
        historyLog.setBusinessType(sysHistoryLog.businessType());
        //操作类型
        historyLog.setOperateType(sysHistoryLog.operateType().getValue());
        //设置业务主键businessId，这里从原对象获取插入数据库后的id,mybatis务必设置主键回填
        Long primaryKey = getPrimaryKey(point, sysHistoryLog);
        historyLog.setBusinessId(primaryKey);
        //新增没有原对象
        if (sysHistoryLog.operateType() != SysHistoryLog.OperateType.ADD) {
            setOriginObj(sysHistoryLog, primaryKey, serviceLoader, historyLog);
        }
        //删除是没有新对象
        if (sysHistoryLog.operateType() != SysHistoryLog.OperateType.DELETE) {
            setNewObj(point, sysHistoryLog, historyLog);
        }
        return historyLog;

    }

    private void setOriginObj(SysHistoryLog sysHistoryLog, Long primaryKey, ServiceLoader<ExtendLogField> serviceLoader, HistoryLog historyLog) {
        String tableName = sysHistoryLog.tableName();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from")
                .append(StrPool.C_SPACE)
                .append(tableName)
                .append(StrPool.C_SPACE)
                .append("where")
                .append(StrPool.C_SPACE)
                .append(sysHistoryLog.primaryKeyColumn())
                .append("=")
                .append(primaryKey);
        serviceLoader.findFirst().ifPresent(extendLogField -> historyLog.setOriginObj(extendLogField.originObj(sqlBuilder.toString())));
    }

    /**
     * @param point
     * @param sysHistoryLog
     * @param historyLog
     * @return
     */
  protected abstract   HistoryLog setNewObj(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog, HistoryLog historyLog);


    /**
     * 获取当前操作对象的业务主键，mybatis务必设置主键回填，保证新增对象能获取到主键
     *
     * @param point
     * @param sysHistoryLog
     * @return
     */
    protected Long getPrimaryKey(ProceedingJoinPoint point, SysHistoryLog sysHistoryLog) {
        Field primaryField;
        Object primaryKey;
        //Controller方法传递的参数
        Object targetParam = point.getArgs()[sysHistoryLog.targetParamIndex()];
        String primaryKeyFieldName = sysHistoryLog.primaryKeyField();
        //若是删除，则被操作的参数就是主键id
        if (sysHistoryLog.operateType() == SysHistoryLog.OperateType.DELETE) {
            return (Long) targetParam;
        } else {//新增、更新的话主键从被操作的目标参数中获取
            try {
                primaryField = targetParam.getClass().getDeclaredField(primaryKeyFieldName);
                primaryField.setAccessible(true);
                primaryKey = primaryField.get(targetParam);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new BusinessException(e);
            }
        }
        return Long.valueOf(primaryKey.toString());
    }
}

