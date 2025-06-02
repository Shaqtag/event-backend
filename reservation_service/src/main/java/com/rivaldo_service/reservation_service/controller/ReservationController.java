package com.rivaldo_service.reservation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rivaldo_service.reservation_service.model.Reservation;
import com.rivaldo_service.reservation_service.service.ReservationService;

import java.util.List;


@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> creerReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.creerReservation(reservation));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reservation>> getReservationsClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reservationService.getReservationsParClient(clientId));
    }

    @GetMapping("/materiel/{materielId}")
    public ResponseEntity<List<Reservation>> getReservationsMateriel(@PathVariable Long prestataireId) {
        return ResponseEntity.ok(reservationService.getReservationsParPrestataire(prestataireId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }*/

    @DeleteMapping("/{id}") // L'endpoint reste DELETE comme demandé, mais l'action est une annulation logique
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        Reservation updatedReservation = reservationService.annulerReservation(id);
        if (updatedReservation != null) {
            // Retourne la réservation mise à jour avec le statut ANNULEE
            return ResponseEntity.ok(updatedReservation);
        }
        // Si la réservation n'est pas trouvée par l'ID
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
