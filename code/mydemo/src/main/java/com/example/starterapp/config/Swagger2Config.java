package com.example.starterapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    // 是否开启swagger, 一般正式环境是需要关闭的
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.example.starterapp.controller"))
                // 指定路径处理 PathSelectors.any()代表所有路径
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket userDocket(){
        Parameter parameter = new ParameterBuilder().name("token")
                .description("用户登录令牌")
                .parameterType("header")
                .modelRef(new ModelRef("String"))
                .required(true)
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .groupName("用户组")
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("com.example.starterapp.controller"))
                   .paths(PathSelectors.ant("/user/**"))
                   .build()
                   .globalOperationParameters(parameters);
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger集成和使用")
                .description("demo示例")
                .contact(new Contact("周碧辉", "www.baidu.com", "17786611658@163.com"))
                .version("1.0.0")
                .build();
    }
}
