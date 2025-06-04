package com.rivaldo_service.prestation_service.repository.jpa;


import com.rivaldo_service.prestation_service.model.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {

    // Filtrer les prestataires par localisation (recherche exacte)
    List<Prestataire> findByLocalisation(String localisation);

    // Filtrer les prestataires par localisation (recherche partielle, insensible à la casse)
    List<Prestataire> findByLocalisationContainingIgnoreCase(String localisation);
    // Filtrer les prestataires par nom (recherche exacte)
    List<Prestataire> findByNom(String nom);
    // Filtrer les prestataires par nom (recherche partielle, insensible à la casse)
    List<Prestataire> findByNomContainingIgnoreCase(String nom);
    // Filtrer les prestataires par contact (recherche exacte)
    List<Prestataire> findByContact(String contact);
    // Filtrer les prestataires par contact (recherche partielle, insensible à la case)
    List<Prestataire> findByContactContainingIgnoreCase(String contact);
    //Filtrer les prestataires par id
    //List<Prestataire> findById(Long id);
}

