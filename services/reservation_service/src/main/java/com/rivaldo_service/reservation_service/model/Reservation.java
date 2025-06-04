package com.rivaldo_service.reservation_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Long userId;       // ID du client (lié au microservice Client)
    private Long prestataireId;     // ID du prestataire (lié au microservice Prestataire)
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String statut;       // "CONFIRMEE", "ANNULEE", "EN_ATTENTE"
    
}

