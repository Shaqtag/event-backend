package com.rivaldo_service.prestation_service.controller;

import com.rivaldo_service.prestation_service.model.Service;
import com.rivaldo_service.prestation_service.repository.mongo.ServiceRepository;
import com.rivaldo_service.prestation_service.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public ServiceController(ServiceRepository serviceRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.serviceRepository = serviceRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @PostMapping
    public ResponseEntity<Service> createService(@Valid @RequestBody Service service) {
        service.setId(sequenceGeneratorService.generateSequence(Service.SEQUENCE_NAME));
        Service saved = serviceRepository.save(service);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @Valid @RequestBody Service service) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    existingService.setNomService(service.getNomService());
                    existingService.setDescription(service.getDescription());
                    Service updated = serviceRepository.save(existingService);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteService(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    serviceRepository.delete(existingService);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Service>> searchServices(@RequestParam(required = false) String nomService) {
        if (nomService != null && !nomService.isEmpty()) {
            List<Service> services = serviceRepository.findByNomServiceContainingIgnoreCase(nomService);
            return ResponseEntity.ok(services);
        } else {
            return ResponseEntity.ok(serviceRepository.findAll());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countServices() {
        long count = serviceRepository.count();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsServiceById(@PathVariable Long id) {
        boolean exists = serviceRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }
    
}