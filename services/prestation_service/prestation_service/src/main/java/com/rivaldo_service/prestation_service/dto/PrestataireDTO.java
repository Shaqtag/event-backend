package com.rivaldo_service.prestation_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestataireDTO {

    private Long id;
    private String nom;
    private String localisation;
    private String contact;

    // Constructeurs
    public PrestataireDTO() {}

    public PrestataireDTO(Long id, String nom, String localisation, String contact) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.contact = contact;
    }
}
