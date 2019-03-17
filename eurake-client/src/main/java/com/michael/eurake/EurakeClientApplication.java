package com.michael.eurake;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurakeClientApplication {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String port = scanner.nextLine();
		//run as application follow the input port number
		new SpringApplicationBuilder(EurakeClientApplication.class).properties("server.port=" + port).run(args);
		
//		SpringApplication.run(EurakeClientApplication.class, args);
	}

}
