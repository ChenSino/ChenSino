package com.chensino.common.log;

import com.chensino.common.log.aspect.OperateLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenkun
 * @Description log自动配置类
 * @date 2022/7/28 下午9:54
 */
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    @Bean
    public OperateLogAspect getOperateLogAspect(ApplicationEventPublisher publisher){
        return new OperateLogAspect(publisher);
    }
}
