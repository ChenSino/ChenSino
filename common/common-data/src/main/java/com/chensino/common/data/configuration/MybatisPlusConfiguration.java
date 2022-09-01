package com.chensino.common.data.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MybatisPlusConfiguration {

    public MybatisPlusConfiguration(){
       log.debug("Init MybatisPlusConfiguration ……");
    }
}
