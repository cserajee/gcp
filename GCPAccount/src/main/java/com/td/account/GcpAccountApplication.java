package com.td.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GcpAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcpAccountApplication.class, args);
	}

}
