package com.projet.facture.service_facturation.repository;

import com.projet.facture.service_facturation.modele.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository pour gérer les opérations CRUD sur les factures
public interface RepositoryFacture extends JpaRepository<Facture, Long> {
    // Recherche des factures par identifiant de client
    List<Facture> findByIdentifiantClient(String identifiantClient);
}