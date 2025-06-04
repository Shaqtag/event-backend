package com.rivaldo_service.reservation_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rivaldo_service.reservation_service.model.Reservation;
import com.rivaldo_service.reservation_service.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        reservation = new Reservation();
        reservation.setUserId(1L);
        reservation.setPrestataireId(2L);
        reservation.setDateDebut(LocalDateTime.of(2025, 6, 5, 10, 0));
        reservation.setDateFin(LocalDateTime.of(2025, 6, 5, 12, 0));
        reservation.setStatut("EN_ATTENTE");
    }

    @Test
    void createReservation_shouldPersistAndReturnReservation() throws Exception {
        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"));

        List<Reservation> reservations = reservationRepository.findAll();
        assertEquals(1, reservations.size());
        assertEquals(1L, reservations.get(0).getUserId());
    }

    @Test
    void getReservationsByClientId_shouldReturnReservations() throws Exception {
        reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].statut").value("EN_ATTENTE"));
    }

    @Test
    void getReservationsByPrestataireId_shouldReturnReservations() throws Exception {
        reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations/materiel/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prestataireId").value(2))
                .andExpect(jsonPath("$[0].statut").value("EN_ATTENTE"));
    }

    @Test
    void getAllReservations_shouldReturnAllReservations() throws Exception {
        reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].prestataireId").value(2));
    }

    @Test
    void getReservationById_shouldReturnReservation() throws Exception {
        Reservation saved = reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations/{id}", saved.getReservationId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(saved.getReservationId()))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void getReservationById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/reservations/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cancelReservation_shouldUpdateStatusToAnnulee() throws Exception {
        Reservation saved = reservationRepository.save(reservation);

        mockMvc.perform(delete("/api/reservations/{id}", saved.getReservationId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("ANNULEE"));

        Reservation updated = reservationRepository.findById(saved.getReservationId()).orElse(null);
        assertEquals("ANNULEE", updated.getStatut());
    }

    @Test
    void cancelReservation_shouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/reservations/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateReservation_shouldUpdateReservation() throws Exception {
        Reservation saved = reservationRepository.save(reservation);
        Reservation updatedReservation = new Reservation();
        updatedReservation.setUserId(3L);
        updatedReservation.setPrestataireId(4L);
        updatedReservation.setDateDebut(LocalDateTime.of(2025, 6, 6, 10, 0));
        updatedReservation.setDateFin(LocalDateTime.of(2025, 6, 6, 12, 0));
        updatedReservation.setStatut("CONFIRMEE");

        mockMvc.perform(put("/api/reservations/{id}", saved.getReservationId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.statut").value("CONFIRMEE"));

        Reservation updated = reservationRepository.findById(saved.getReservationId()).orElse(null);
        assertEquals(3L, updated.getUserId());
        assertEquals("CONFIRMEE", updated.getStatut());
    }

    @Test
    void updateReservation_shouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/reservations/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isNotFound());
    }
}