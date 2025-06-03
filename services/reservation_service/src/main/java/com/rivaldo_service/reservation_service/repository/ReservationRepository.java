package com.rivaldo_service.reservation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rivaldo_service.reservation_service.model.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByPrestataireId(Long prestataireId);
}
