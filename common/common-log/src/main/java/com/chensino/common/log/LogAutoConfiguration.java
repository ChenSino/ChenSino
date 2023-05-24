package com.chensino.common.log;

import com.chensino.common.log.configuration.CustomLogProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenkun
 * @Description log自动配置类
 * @Date 2022/7/28 下午9:54
 */
@Configuration
@ConditionalOnWebApplication
@ComponentScan("com.chensino.common.log")
@MapperScan("com.chensino.common.log.mapper")
@EnableConfigurationProperties({CustomLogProperties.class})
public class LogAutoConfiguration {
}
