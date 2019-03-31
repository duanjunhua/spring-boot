package com.michael.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class HystrixClientActuatorApplication {

	/**
	 * Access Dashboard address: http://localhost:8085/hystrix
	 */
	public static void main(String[] args) {
		SpringApplication.run(HystrixClientActuatorApplication.class, args);
	}

}
