package com.chensino.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 204506
 */
@SpringBootApplication
@MapperScan("com.chensino.core.system.mapper")
@MapperScan("net.maku.generator.dao")
public class App   {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
