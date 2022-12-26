package com.chensino.core.extendpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    /**
     * Springboot启动后做一些处理工作
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("springboot启动了。。。执行自定义ApplicationRunner");
        System.out.println(args.getNonOptionArgs());
        System.out.println(args.getSourceArgs());
        System.out.println(args.getOptionNames());
    }
}
