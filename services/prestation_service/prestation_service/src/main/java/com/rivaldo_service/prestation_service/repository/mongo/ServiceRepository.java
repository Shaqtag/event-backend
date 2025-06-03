package com.rivaldo_service.prestation_service.repository.mongo;

import com.rivaldo_service.prestation_service.model.Service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<Service, Long> {
    // Add custom query methods here
    // For example, you can add methods to find services by name or description
    List<Service> findByNomServiceContainingIgnoreCase(String nomService);
    List<Service> findByDescriptionContainingIgnoreCase(String description);
    
}
