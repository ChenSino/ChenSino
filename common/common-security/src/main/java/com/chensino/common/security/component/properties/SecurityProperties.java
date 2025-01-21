package com.chensino.common.security.component.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author chenkun
 * @Date 2023/5/5 上午10:30
 */
@ConfigurationProperties(prefix = "security")
@Data
@Configuration
public class SecurityProperties {
    /**
     * api白名单
     */
    private List<String> whiteList;

    /**
     * 静态资源白名单
     */
    private List<String> staticUrlList;
}
