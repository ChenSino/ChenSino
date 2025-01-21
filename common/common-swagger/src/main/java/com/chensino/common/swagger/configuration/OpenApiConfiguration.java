package com.chensino.common.swagger.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
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
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("ChenSino API")
                .description("ChenSino接口文档说明")
                .version("v0.0.1-SNAPSHOT")
                .license(new License().name("Chensino博客")
                        .url("https://chensino.github.io"));
        final String securitySchemeName = "xTokenAuth";
        Components components = new Components()
                .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name("X-token") // 请求头名称
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER) // 请求头位置
                );
        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(components);
    }

    /**
     * demo 分组
     *
     * @return demo分组接口
     */
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("登录接口")
                .pathsToMatch("/login/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理接口")
                .pathsToMatch("/user/**")
                .build();
    }

    /**
     * minio接口 分组
     *
     * @return minio接口
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("minio接口")
                .pathsToMatch("/minio/**")
                .build();
    }
}
