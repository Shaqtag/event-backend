package com.rivaldo_service.prestation_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prestataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prestataireId;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotBlank(message = "La localisation ne peut pas être vide")
    private String localisation;

    @NotBlank(message = "Le contact ne peut pas être vide")
    private String contact;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String motDePasse;

    // Constructeurs
    public Prestataire() {}

    public Prestataire(String nom, String localisation, String contact, String motDePasse) {
        this.nom = nom;
        this.localisation = localisation;
        this.contact = contact;
        this.motDePasse = motDePasse;
    }
}