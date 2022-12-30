package com.chensino.core.extendpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PrintApplicationArgs {

    /**
     * 在一个bean注入ApplicationArguments，可以获得启动时传递的参数
     *
     * @param arguments
     */
    public PrintApplicationArgs(ApplicationArguments arguments) {
        log.info("启动参数查看：{}", arguments.getNonOptionArgs());
    }
}

