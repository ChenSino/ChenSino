package com.chensino.common.log.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenkun
 * @Description
 * @date 2023/5/23 下午9:44
 */
@ConfigurationProperties(prefix = "chensino.common.log")
@Data
@Component
public class CustomLogProperties {

    @Value("${chensino.common.log:t_operate_log}")
    private String logTableName;
}
