package com.demo.swagger;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * @author Michael
 * @Date: 2018年12月4日_下午2:15:48
 * @Version: v0.1
 */
@Component
@Configuration
public class SwaggerConfig {

	@Value("${spring.cloud.config.profile}")
	private String env;
	
	public static final String[] TEST_ENV = {"DEV", "SIT", "UAT"};
	
	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.enable(nonePrdEnv())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.demo.controller"))
				.paths(PathSelectors.any())				
				.build();
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Test Swagger API")
				.contact(new Contact("Michael J H Duan", "http://www.baidu.com", "michael@test.com"))
				.description("Test Demo")
				.version("v0.1")
				.termsOfServiceUrl("http://www,baidu.com")
				.build();
	}
	
	private Boolean nonePrdEnv() {
		return ArrayUtils.contains(SwaggerConfig.TEST_ENV, env.toUpperCase()) ;
	}
}
