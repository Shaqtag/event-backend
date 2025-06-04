package com.rivaldo_service.prestation_service.repository.mongo;

import com.rivaldo_service.prestation_service.model.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    private Service service;

    @BeforeEach
    void setUp() {
        serviceRepository.deleteAll();
        service = new Service(1L, "Catering", "Service de restauration");
    }

    @Test
    void saveService_shouldPersistService() {
        Service saved = serviceRepository.save(service);

        assertNotNull(saved);
        assertEquals("Catering", saved.getNomService());
    }

    @Test
    void findById_shouldReturnService() {
        serviceRepository.save(service);

        Service found = serviceRepository.findById(1L).orElse(null);

        assertNotNull(found);
        assertEquals("Catering", found.getNomService());
    }

    @Test
    void findAll_shouldReturnAllServices() {
        serviceRepository.save(service);

        List<Service> services = serviceRepository.findAll();

        assertEquals(1, services.size());
        assertEquals("Catering", services.get(0).getNomService());
    }

    @Test
    void findByNomServiceContainingIgnoreCase_shouldReturnMatchingServices() {
        serviceRepository.save(service);

        List<Service> services = serviceRepository.findByNomServiceContainingIgnoreCase("cater");

        assertEquals(1, services.size());
        assertEquals("Catering", services.get(0).getNomService());
    }

    @Test
    void deleteById_shouldRemoveService() {
        serviceRepository.save(service);

        serviceRepository.deleteById(1L);
        Service found = serviceRepository.findById(1L).orElse(null);

        assertNull(found);
    }

    @Test
    void count_shouldReturnCorrectCount() {
        serviceRepository.save(service);

        long count = serviceRepository.count();

        assertEquals(1, count);
    }

    @Test
    void existsById_shouldReturnTrue() {
        serviceRepository.save(service);

        boolean exists = serviceRepository.existsById(1L);

        assertTrue(exists);
    }
}