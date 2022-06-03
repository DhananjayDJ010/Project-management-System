package com.project.config;


import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableSwagger2
public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage( "com.project"))
//                .paths(PathSelectors.any())
//                .build();
//    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Project-Management-System")
                .packagesToScan("com.project")
                .pathsToMatch("/**")
                .build();
    }
}


