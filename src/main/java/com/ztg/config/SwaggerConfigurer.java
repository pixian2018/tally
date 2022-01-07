package com.ztg.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description: Swagger配置类
 * @author: zhoutg
 * @time: 2018/8/2 14:21
 */
@Configuration
@ConditionalOnProperty(prefix = "swagger", value = { "enable" }, havingValue = "true")
@EnableOpenApi
public class SwaggerConfigurer {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30) // 3.0配置
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ztg.web")) // 扫描包路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("记账服务 api ")
                .description("记账服务")
                .termsOfServiceUrl("")
                .contact(new Contact("tally","",""))
                .version("1.0")
                .build();
    }
}
