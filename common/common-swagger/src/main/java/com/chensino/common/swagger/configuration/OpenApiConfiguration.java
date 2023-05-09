package com.chensino.common.swagger.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenkun
 * @createDate 2023/5/9 上午9:42
 */
@Configuration
public class OpenApiConfiguration {
    /**
     * SpringDoc 标题、描述、版本等信息配置
     *
     * @return openApi 配置信息
     */
    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("ChenSino API")
                        .description("ChenSino接口文档说明")
                        .version("v0.0.1-SNAPSHOT")
                        .license(new License().name("Chensino博客")
                                .url("https://chensino.github.io")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub项目地址")
                        .url("https://github.com/ChenSino/ChenSino"))
                // 配置Authorizations
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")));
    }

  /*  *//**
     * demo 分组
     *
     * @return demo分组接口
     *//*
    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
                .group("登录接口")
                .pathsToMatch("/login/**")
                .build();
    }

    *//**
     * minio接口 分组
     *
     * @return minio接口
     *//*
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("minio接口")
                .pathsToMatch("/minio/**")
                .build();
    }*/
}
