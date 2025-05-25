package com.projet.service_facturation.modele;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// Entité représentant une facture dans la base de données
@Entity
@Table(name = "invoices")
public class Invoice {

    // Identifiant unique de la facture, généré automatiquement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identifiant du client (non null)
    @Column(nullable = false)
    private String clientId;

    // Montant total de la facture (non null)
    @Column(nullable = false)
    private Double amount;

    // Statut de la facture (PENDING, PAID, CANCELED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    // Date de création de la facture
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Liste des identifiants des prestations réservées, stockée en JSONB
    @Column(columnDefinition = "jsonb")
    private List<String> serviceIds;

    // Constructeur par défaut, initialise la date et le statut
    public Invoice() {
        this.createdAt = LocalDateTime.now();
        this.status = InvoiceStatus.PENDING;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }
}

// Enumération pour les statuts possibles de la facture
enum InvoiceStatus {
    PENDING, PAID, CANCELED
}