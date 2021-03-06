package com.michael.zipkin.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zipkin.consumer.controller"))
				.paths(PathSelectors.any())				
				.build();
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Test Zipkin Consumer")
				.contact(new Contact("Michael J H Duan", "http://www.baidu.com", "michael@test.com"))
				.description("Test Zipkin")
				.version("v0.1")
				.termsOfServiceUrl("http://www,baidu.com")
				.build();
	}
}
