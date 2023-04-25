package com.visual.face.search.server.bootstrap.conf;

import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.builders.RequestHandlerSelectors;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

@Configuration
@EnableOpenApi
@EnableKnife4j
public class Knife4jConfig {

    @Value("${visual.swagger.enable:true}")
    private Boolean enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(new ApiInfoBuilder()
                    .title("人脸搜索服务API")
                    .description("人脸搜索服务API")
                    .version("2.0.0")
                    .build())
                .groupName("2.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.visual.face.search.server.controller.server"))
                .paths(PathSelectors.any())
                .build();
    }
}


