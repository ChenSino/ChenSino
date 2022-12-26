package com.chensino.core.extendpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 此扩展点和ApplicationRunner功能差不多，都是springboot启动后执行一些逻辑处理
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("执行自定义CommandLineRunner");
        log.info("{}", args);
    }
}
