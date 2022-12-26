package com.chensino.core.extendpoint;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtendPointConfiguration {
    /**
     * 优雅关机时，自定义的退出码
     * @return
     */
    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 10086;
    }
}
