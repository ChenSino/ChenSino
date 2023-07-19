package com.chensino.common.log.annotation;

import cn.hutool.extra.spring.SpringUtil;
import com.chensino.common.log.strategy.AddHistoryLogStrategy;
import com.chensino.common.log.strategy.HistoryLogStrategy;
import com.chensino.common.log.strategy.UpdateHistoryLogStrategy;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * @author chenxk
 * @description 历史记录
 * @createDate 2023/6/13 上午10:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysHistoryLog {


    String value() default "";

    /**
     * 业务类型
     * @return
     */
    String businessType() ;

    /**
     * 关联的业务表中主键column
     *
     * @return
     */
    String primaryKeyColumn() default "id";

    /**
     * 主键对应实体的字段属性名
     * @return
     */
    String primaryKeyField() default "id";

    /**
     * 被操作对象位于Controller方法的index
     *
     * @return
     */
    int targetParamIndex() default 0;

    /**
     * 操作类型，增加/删除/修改
     *
     * @return
     */
    OperateType operateType() default OperateType.UPDATE;

    String tableName();

    @Getter
    enum OperateType {
        ADD(1, "新增", () -> SpringUtil.getBean(AddHistoryLogStrategy.class)),
        UPDATE(2, "更新", () -> SpringUtil.getBean(UpdateHistoryLogStrategy.class)),
        DELETE(3, "删除", () -> SpringUtil.getBean(UpdateHistoryLogStrategy.class));

        private final int value;
        private final String desc;
        private final Supplier<HistoryLogStrategy> strategySupplier;

        OperateType(int value, String desc, Supplier<HistoryLogStrategy> strategySupplier) {
            this.value = value;
            this.desc = desc;
            this.strategySupplier = strategySupplier;
        }

    }
}
