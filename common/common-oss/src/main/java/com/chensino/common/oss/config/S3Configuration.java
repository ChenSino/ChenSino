package com.chensino.common.oss.config;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(S3Properties.class)
@ComponentScan(basePackages = {"com.chensino.common.oss.service"})
@Slf4j
public class S3Configuration {

    private final S3Properties s3Properties;


    @Bean
    @Lazy(value = false)
    public S3Client createS3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .region(Region.US_EAST_1) // 使用MinIO时可以任意选择区域
                .forcePathStyle(true)

                .build();
    }
}