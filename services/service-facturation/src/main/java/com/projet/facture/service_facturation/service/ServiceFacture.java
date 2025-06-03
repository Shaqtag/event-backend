package com.projet.facture.service_facturation.service;

import com.projet.facture.service_facturation.modele.Facture;
import com.projet.facture.service_facturation.modele.StatutFacture;
import com.projet.facture.service_facturation.repository.RepositoryFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service contenant la logique métier pour les factures
@Service
public class ServiceFacture {

    // Injection du repository pour accéder à la base de données
    @Autowired
    private RepositoryFacture repositoryFacture;

    // Crée une nouvelle facture avec validation des champs requis
    public Facture creerFacture(Facture facture) {
        if (facture.getIdentifiantClient() == null || facture.getMontant() == null || 
            facture.getIdentifiantsPrestations() == null || facture.getIdentifiantsPrestations().isEmpty()) {
            throw new IllegalArgumentException("Champs requis manquants : identifiantClient, montant, ou identifiantsPrestations");
        }
        return repositoryFacture.save(facture);
    }

    // Récupère toutes les factures ou celles d’un client spécifique
    public List<Facture> obtenirToutesFactures(String identifiantClient) {
        if (identifiantClient != null) {
            return repositoryFacture.findByIdentifiantClient(identifiantClient);
        }
        return repositoryFacture.findAll();
    }

    // Met à jour le statut d’une facture
    public Facture mettreAJourStatutFacture(Long identifiant, StatutFacture statut) {
        Optional<Facture> factureOptionnelle = repositoryFacture.findById(identifiant);
        if (factureOptionnelle.isEmpty()) {
            throw new IllegalArgumentException("Facture non trouvée");
        }
        Facture facture = factureOptionnelle.get();
        facture.setStatut(statut);
        return repositoryFacture.save(facture);
    }

    // Supprime une facture par son ID
    public void supprimerFacture(Long identifiant) {
        if (!repositoryFacture.existsById(identifiant)) {
            throw new IllegalArgumentException("Facture non trouvée");
        }
        repositoryFacture.deleteById(identifiant);
    }
}