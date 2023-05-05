package com.chensino.core.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author chenkun
 * @createDate 2023/5/5 上午10:30
 */
@ConfigurationProperties(prefix = "security")
@Data
@Configuration
public class SecurityProperties {
    /**
     * 白名单
     */
    private List<String> whiteList;
}
