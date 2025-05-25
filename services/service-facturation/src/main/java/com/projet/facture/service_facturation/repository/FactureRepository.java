package com.projet.service_facturation.repository;

import com.example.billing.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository pour gérer les opérations CRUD sur les factures
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Recherche des factures par identifiant de client
    List<Invoice> findByClientId(String clientId);
}