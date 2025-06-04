package com.rivaldo_service.prestation_service.controller;

import com.rivaldo_service.prestation_service.model.Service;
import com.rivaldo_service.prestation_service.repository.mongo.ServiceRepository;
import com.rivaldo_service.prestation_service.service.SequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private ServiceController serviceController;

    private MockMvc mockMvc;

    private Service service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
        service = new Service(1L, "Catering", "Service de restauration");
    }

    @Test
    void createService_shouldReturnCreatedService() throws Exception {
        when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1L);
        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        mockMvc.perform(post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomService\":\"Catering\",\"description\":\"Service de restauration\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value(1L))
                .andExpect(jsonPath("$.nomService").value("Catering"));
    }

    @Test
    void getServiceById_shouldReturnService() throws Exception {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        mockMvc.perform(get("/api/services/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value(1L))
                .andExpect(jsonPath("$.nomService").value("Catering"));
    }

    @Test
    void getServiceById_shouldReturnNotFound() throws Exception {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/services/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllServices_shouldReturnListOfServices() throws Exception {
        when(serviceRepository.findAll()).thenReturn(Arrays.asList(service));

        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceId").value(1L))
                .andExpect(jsonPath("$[0].nomService").value("Catering"));
    }

    @Test
    void updateService_shouldReturnUpdatedService() throws Exception {
        Service updatedService = new Service(1L, "Catering Updated", "Updated description");
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(serviceRepository.save(any(Service.class))).thenReturn(updatedService);

        mockMvc.perform(put("/api/services/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomService\":\"Catering Updated\",\"description\":\"Updated description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomService").value("Catering Updated"));
    }

    @Test
    void deleteService_shouldReturnNoContent() throws Exception {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        mockMvc.perform(delete("/api/services/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchServices_byNomService_shouldReturnMatchingServices() throws Exception {
        when(serviceRepository.findByNomServiceContainingIgnoreCase("Catering")).thenReturn(Arrays.asList(service));

        mockMvc.perform(get("/api/services/search?nomService=Catering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomService").value("Catering"));
    }

    @Test
    void countServices_shouldReturnCount() throws Exception {
        when(serviceRepository.count()).thenReturn(1L);

        mockMvc.perform(get("/api/services/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void existsServiceById_shouldReturnTrue() throws Exception {
        when(serviceRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(get("/api/services/exists/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}