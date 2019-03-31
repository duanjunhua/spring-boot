package com.michael.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulRouterApplication {

	/**
	 *	default use HttpClient, can use other client, e.g.:
	 *	properties:
	 *		ribbon.okhttp.enabled=true
	 *	dependency:
	 * 		<groupId>com.squareup.okhttp3</groupId>
	 * 		<artifactId>okhttp</artifactId>
	 */
	public static void main(String[] args) {
		SpringApplication.run(ZuulRouterApplication.class, args);
	}

}
