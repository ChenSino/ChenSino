package com.chensino.common.oss.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(MinioProperties.class)
@ComponentScan(basePackages = {"com.chensino.common.oss.service"})
@Slf4j
public class MinoConfiguration {

    private final MinioProperties minioProperties;

    @Bean
    @Lazy(value = false)
    public MinioClient getMinClient() {
        log.info("初始化minio客户端");
        MinioClient build = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint(), minioProperties.getPort(), minioProperties.getSecure())//http
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return build;
    }
}
