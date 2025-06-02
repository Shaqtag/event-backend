package com.rivaldo_service.prestation_service.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.rivaldo_service.prestation_service.repository.mongo")
public class MongoConfig {
}
