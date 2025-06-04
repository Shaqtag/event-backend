package com.rivaldo_service.prestation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PrestationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrestationServiceApplication.class, args);
	}

}
