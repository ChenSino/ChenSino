package com.chensino.common.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chensino.common.log.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenkun
 * @Date 2023/5/6 下午5:24
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
}
