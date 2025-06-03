package com.projet.facture.service_facturation.controller;

import com.projet.facture.service_facturation.modele.Facture;
import com.projet.facture.service_facturation.modele.StatutFacture;
import com.projet.facture.service_facturation.service.ServiceFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Contrôleur REST pour gérer les endpoints liés aux factures
@RestController
@RequestMapping("/factures")
public class ControleurFacture {

    // Injection du service pour la logique métier
    @Autowired
    private ServiceFacture serviceFacture;

    // Crée une nouvelle facture
    @PostMapping
    public ResponseEntity<Facture> creerFacture(@RequestBody Facture facture) {
        try {
            System.out.println(facture); // Debug
            Facture factureCreee = serviceFacture.creerFacture(facture);
            return new ResponseEntity<>(factureCreee, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Liste toutes les factures ou celles d’un client spécifique
    @GetMapping
    public ResponseEntity<List<Facture>> obtenirToutesFactures(@RequestParam MultiValueMap<String, String> params) {
        // Gérer les cas où params est null en initialisant une valeur par défaut
        MultiValueMap<String, String> safeParams = (params != null) ? params : new LinkedMultiValueMap<>();
        String identifiantClient = safeParams.getFirst("identifiantClient");
        List<Facture> factures = serviceFacture.obtenirToutesFactures(identifiantClient);
        return new ResponseEntity<>(factures, HttpStatus.OK);
    }

    // Met à jour le statut d’une facture
    @PutMapping("/{identifiant}")
    public ResponseEntity<Facture> mettreAJourStatutFacture(@PathVariable Long identifiant, @RequestBody StatutFacture statut) {
        try {
            Facture factureMiseAJour = serviceFacture.mettreAJourStatutFacture(identifiant, statut);
            return new ResponseEntity<>(factureMiseAJour, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Supprime une facture
    @DeleteMapping("/{identifiant}")
    public ResponseEntity<String> supprimerFacture(@PathVariable Long identifiant) {
        try {
            serviceFacture.supprimerFacture(identifiant);
            return new ResponseEntity<>("Facture supprimée avec succès", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Facture non trouvée", HttpStatus.NOT_FOUND);
        }
    }
}