package com.chensino.common.oss.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(S3Properties.class)
@ComponentScan(basePackages = {"com.chensino.common.oss.service"})
@Slf4j
public class S3Configuration {

    private final S3Properties s3Properties;


    @Bean
    @Lazy(value = false)
    public AmazonS3 getAmazonS3() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(s3Properties.getMaxConnections());
        //使用http,mino默认是http
        clientConfiguration.setProtocol(Protocol.HTTP);

        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                s3Properties.getEndpoint(), s3Properties.getRegion());

        return AmazonS3ClientBuilder.standard()
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withPathStyleAccessEnabled(true)
                .build();

    }
}
