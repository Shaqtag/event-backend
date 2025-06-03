package com.rivaldo_service.prestation_service.config;



import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.rivaldo_service.prestation_service.repository.jpa")
@EntityScan(basePackages = "com.rivaldo_service.prestation_service.model")
public class DatabaseConfig {
}
