package com.rivaldo_service.prestation_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
@Getter
@Setter
public class Service {

    public static final String SEQUENCE_NAME = "services_sequence";

    @Id
    private Long serviceId;

    @NotBlank(message = "Le nom du service ne peut pas être vide")
    private String nomService;

    @NotBlank(message = "La description ne peut pas être vide")
    private String description;

    // Constructeurs
    public Service() {}

    public Service(Long id, String nomService, String description) {
        this.serviceId = id;
        this.nomService = nomService;
        this.description = description;
    }
}
