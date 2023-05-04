package com.chensino.core.api.configuration;

import com.chensino.core.api.validate.UserLoginDTOValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2023/5/4 上午9:25
 * @Created by chenxk
 */
@Configuration
public class CoreApiConfiguration {

    @Bean
    public UserLoginDTOValidator userLoginDTOValidator(){
        return new UserLoginDTOValidator();
    }
}
