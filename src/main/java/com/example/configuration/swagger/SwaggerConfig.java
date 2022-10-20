package com.example.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	            "Spring Boot - HypeApp - REST API",
	            "Spring boot - HypeApp - REST API - Documentation",
	            "1",
	            "Terms of service",
	            new Contact("David Palanca y Rafa Rodriguez",
	                    "https://github.com/Cortadai",
	                    "david.cortabarria@gmail.com"),
	            "License of API",
	            "API license URL",
	            Collections.emptyList()
	    );
	}
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .securityContexts(Collections.singletonList(securityContext()))
//                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}