package com.rivaldo_service.reservation_service.service;

//import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;

import com.rivaldo_service.reservation_service.model.Reservation;
import com.rivaldo_service.reservation_service.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation creerReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsParClient(Long clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    public List<Reservation> getReservationsParPrestataire(Long prestataireId) {
        return reservationRepository.findByPrestataireId(prestataireId);
    
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    /*public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }*/

    public Reservation annulerReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            reservation.setStatut("ANNULEE"); // Met à jour le statut de la réservation
            return reservationRepository.save(reservation); // Enregistre la réservation mise à jour
        }
        return null; // Retourne null si la réservation n'est pas trouvée
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id).orElse(null);
        if (existingReservation != null) {
            existingReservation.setUserId(reservation.getUserId());
            existingReservation.setPrestataireId(reservation.getPrestataireId());
            existingReservation.setDateDebut(reservation.getDateDebut());
            existingReservation.setDateFin(reservation.getDateFin());
            return reservationRepository.save(existingReservation);
        }
        return null;
    }
    
}
