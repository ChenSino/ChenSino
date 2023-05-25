package com.chensino.core;

import jakarta.annotation.Resource;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenkun
 * @createDate 2023/5/25 上午9:56
 */

@SpringBootTest(classes = com.chensino.core.App.class)
public class AppTest {

    @Resource
    StringEncryptor encryptor;

    @Test
    public void encrypt() {
        String username = encryptor.encrypt("root");
        String pwd = encryptor.encrypt("root");
        System.out.println("username = " + username);
        System.out.println("pwd = " + pwd);
    }
}
