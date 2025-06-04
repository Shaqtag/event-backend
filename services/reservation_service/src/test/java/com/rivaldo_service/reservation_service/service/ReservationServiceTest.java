package com.rivaldo_service.reservation_service.service;

import com.rivaldo_service.reservation_service.model.Reservation;
import com.rivaldo_service.reservation_service.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Initialisation explicite des champs de Reservation
        reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setUserId(1L);
        reservation.setPrestataireId(2L);
        reservation.setDateDebut(LocalDateTime.of(2025, 6, 10, 10, 0));
        reservation.setDateFin(LocalDateTime.of(2025, 6, 11, 10, 0));
        reservation.setStatut("CONFIRMEE");
    }

    @Test
    void creerReservation_shouldSaveAndReturnReservation() {
        // Arrange
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation result = reservationService.creerReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getReservationId());
        assertEquals(1L, result.getUserId());
        assertEquals(2L, result.getPrestataireId());
        assertEquals(LocalDateTime.of(2025, 6, 10, 10, 0), result.getDateDebut());
        assertEquals(LocalDateTime.of(2025, 6, 11, 10, 0), result.getDateFin());
        assertEquals("CONFIRMEE", result.getStatut());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void getReservationsParClient_shouldReturnListOfReservations() {
        // Arrange
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findByUserId(1L)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getReservationsParUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation.getReservationId(), result.get(0).getReservationId());
        assertEquals(reservation.getUserId(), result.get(0).getUserId());
        verify(reservationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getReservationsParClient_shouldReturnEmptyList_whenNoReservationsFound() {
        // Arrange
        when(reservationRepository.findByUserId(1L)).thenReturn(Arrays.asList());

        // Act
        List<Reservation> result = reservationService.getReservationsParUser(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reservationRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getReservationsParPrestataire_shouldReturnListOfReservations() {
        // Arrange
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findByPrestataireId(2L)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getReservationsParPrestataire(2L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation.getReservationId(), result.get(0).getReservationId());
        assertEquals(reservation.getPrestataireId(), result.get(0).getPrestataireId());
        verify(reservationRepository, times(1)).findByPrestataireId(2L);
    }

    @Test
    void getReservationsParPrestataire_shouldReturnEmptyList_whenNoReservationsFound() {
        // Arrange
        when(reservationRepository.findByPrestataireId(2L)).thenReturn(Arrays.asList());

        // Act
        List<Reservation> result = reservationService.getReservationsParPrestataire(2L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reservationRepository, times(1)).findByPrestataireId(2L);
    }

    @Test
    void getAllReservations_shouldReturnAllReservations() {
        // Arrange
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getAllReservations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation.getReservationId(), result.get(0).getReservationId());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void getReservationById_shouldReturnReservation_whenFound() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.getReservationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getReservationId());
        assertEquals("CONFIRMEE", result.getStatut());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void getReservationById_shouldReturnNull_whenNotFound() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Reservation result = reservationService.getReservationById(1L);

        // Assert
        assertNull(result);
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void annulerReservation_shouldUpdateStatusAndReturnReservation_whenFound() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation saved = invocation.getArgument(0);
            saved.setStatut("ANNULEE");
            return saved;
        });

        // Act
        Reservation result = reservationService.annulerReservation(1L);

        // Assert
        assertNotNull(result);
        assertEquals("ANNULEE", result.getStatut());
        assertEquals(1L, result.getReservationId());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void annulerReservation_shouldReturnNull_whenNotFound() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Reservation result = reservationService.annulerReservation(1L);

        // Assert
        assertNull(result);
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void updateReservation_shouldUpdateAndReturnReservation_whenFound() {
        // Arrange
        Reservation updatedReservation = new Reservation();
        updatedReservation.setReservationId(1L);
        updatedReservation.setUserId(3L);
        updatedReservation.setPrestataireId(4L);
        updatedReservation.setDateDebut(LocalDateTime.of(2025, 6, 12, 10, 0));
        updatedReservation.setDateFin(LocalDateTime.of(2025, 6, 13, 10, 0));
        updatedReservation.setStatut("EN_ATTENTE");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updatedReservation);

        // Act
        Reservation result = reservationService.updateReservation(1L, updatedReservation);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getUserId());
        assertEquals(4L, result.getPrestataireId());
        assertEquals(LocalDateTime.of(2025, 6, 12, 10, 0), result.getDateDebut());
        assertEquals(LocalDateTime.of(2025, 6, 13, 10, 0), result.getDateFin());
        assertEquals("EN_ATTENTE", result.getStatut());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void updateReservation_shouldReturnNull_whenNotFound() {
        // Arrange
        Reservation updatedReservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Reservation result = reservationService.updateReservation(1L, updatedReservation);

        // Assert
        assertNull(result);
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).save(any());
    }
}