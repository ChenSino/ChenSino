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
import java.net.URISyntaxException;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(S3Properties.class)
@ComponentScan(basePackages = {"com.chensino.common.oss.service"})
@Slf4j
public class S3Configuration {

    private final S3Properties s3Properties;


    @Bean
    @Lazy(value = false)
    public  S3Client createS3Client() {
        // 创建凭证提供者
        var credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey()));

        // 如果有自定义域名，则使用自定义端点；否则使用默认的区域端点
        URI endpointOverride = null;
        if (s3Properties.getCustomDomain() != null && !s3Properties.getCustomDomain().isEmpty()) {
            try {
                endpointOverride = new URI(s3Properties.getCustomDomain());
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid custom domain URI", e);
            }
        }

        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(s3Properties.getRegion()))
                .forcePathStyle(s3Properties.getPathStyleAccess())
                .endpointOverride(endpointOverride)
                .build();
    }
}