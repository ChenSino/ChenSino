package com.chensino.common.swagger.configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@EnableOpenApi
@Configuration
@Getter
public class SwaggerConfiguration {

    @Value(value = "${spring.application.name}")
    private String businessName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName(businessName)
                //开启
                .enable(true)
                .select()
                //扫描路径下使用@Api的controller
                .apis(requestHandlerPredicate())
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ChenSino接口测试")
                .description("一个很优雅的接口测试文档")
                //设置作者信息
                .contact(new Contact("ChenSino", "https://chensino.github.io/", "462488588@qq.com"))
                .version("1.0")
                .build();
    }

    private Predicate<RequestHandler> requestHandlerPredicate() {
        return requestHandler -> requestHandler.isAnnotatedWith(ApiOperation.class) && requestHandler.findControllerAnnotation(Api.class).isPresent();
    }
}
