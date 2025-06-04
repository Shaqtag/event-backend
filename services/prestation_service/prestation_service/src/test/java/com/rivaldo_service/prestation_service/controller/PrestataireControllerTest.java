package com.rivaldo_service.prestation_service.controller;

import com.rivaldo_service.prestation_service.model.Prestataire;
import com.rivaldo_service.prestation_service.repository.jpa.PrestataireRepository;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PrestataireControllerTest {

    @Mock
    private PrestataireRepository prestataireRepository;

    @InjectMocks
    private PrestataireController prestataireController;

    private MockMvc mockMvc;

    private Prestataire prestataire;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(prestataireController).build();
        prestataire = new Prestataire("John Doe", "Paris", "123456789", "password");
        prestataire.setPrestataireId(1L);
    }

    @Test
    void createPrestataire_shouldReturnCreatedPrestataireDTO() throws Exception {
        when(prestataireRepository.save(any(Prestataire.class))).thenReturn(prestataire);

        mockMvc.perform(post("/api/prestataires")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"John Doe\",\"localisation\":\"Paris\",\"contact\":\"123456789\",\"motDePasse\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prestataireId").value(1L))
                .andExpect(jsonPath("$.nom").value("John Doe"));
    }

    @Test
    void getPrestataireById_shouldReturnPrestataireDTO() throws Exception {
        when(prestataireRepository.findById(1L)).thenReturn(Optional.of(prestataire));

        mockMvc.perform(get("/api/prestataires/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prestataireId").value(1L))
                .andExpect(jsonPath("$.nom").value("John Doe"));
    }

    @Test
    void getPrestataireById_shouldReturnNotFound() throws Exception {
        when(prestataireRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/prestataires/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPrestataires_shouldReturnListOfPrestataireDTOs() throws Exception {
        when(prestataireRepository.findAll()).thenReturn(Arrays.asList(prestataire));

        mockMvc.perform(get("/api/prestataires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prestataireId").value(1L))
                .andExpect(jsonPath("$[0].nom").value("John Doe"));
    }

    @Test
    void getPrestatairesByLocalisation_shouldReturnMatchingPrestataireDTOs() throws Exception {
        when(prestataireRepository.findByLocalisation("Paris")).thenReturn(Arrays.asList(prestataire));

        mockMvc.perform(get("/api/prestataires/search/localisation/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("John Doe"));
    }

    @Test
    void updatePrestataire_shouldReturnUpdatedPrestataireDTO() throws Exception {
        Prestataire updatedPrestataire = new Prestataire("Jane Doe", "Lyon", "987654321", "newpassword");
        updatedPrestataire.setPrestataireId(1L);
        when(prestataireRepository.findById(1L)).thenReturn(Optional.of(prestataire));
        when(prestataireRepository.save(any(Prestataire.class))).thenReturn(updatedPrestataire);

        mockMvc.perform(put("/api/prestataires/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nom\":\"Jane Doe\",\"localisation\":\"Lyon\",\"contact\":\"987654321\",\"motDePasse\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Jane Doe"));
    }

    @Test
    void deletePrestataire_shouldReturnNoContent() throws Exception {
        when(prestataireRepository.findById(1L)).thenReturn(Optional.of(prestataire));

        mockMvc.perform(delete("/api/prestataires/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void countPrestataires_shouldReturnCount() throws Exception {
        when(prestataireRepository.count()).thenReturn(1L);

        mockMvc.perform(get("/api/prestataires/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void existsPrestataireById_shouldReturnTrue() throws Exception {
        when(prestataireRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(get("/api/prestataires/exists/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}