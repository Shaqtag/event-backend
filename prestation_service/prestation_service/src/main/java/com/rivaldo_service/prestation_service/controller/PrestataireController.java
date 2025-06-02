package com.rivaldo_service.prestation_service.controller;

import com.rivaldo_service.prestation_service.dto.PrestataireDTO;
import com.rivaldo_service.prestation_service.model.Prestataire;
import com.rivaldo_service.prestation_service.repository.jpa.PrestataireRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestataires")
public class PrestataireController {

    private final PrestataireRepository prestataireRepository;

    public PrestataireController(PrestataireRepository prestataireRepository) {
        this.prestataireRepository = prestataireRepository;
    }

    // Convertir une entité en DTO
    private PrestataireDTO toDTO(Prestataire prestataire) {
        return new PrestataireDTO(
                prestataire.getId(),
                prestataire.getNom(),
                prestataire.getLocalisation(),
                prestataire.getContact()
        );
    }

    @PostMapping
    public ResponseEntity<PrestataireDTO> createPrestataire(@Valid @RequestBody Prestataire prestataire) {
        Prestataire saved = prestataireRepository.save(prestataire);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestataireDTO> getPrestataireById(@PathVariable Long id) {
        return prestataireRepository.findById(id)
                .map(prestataire -> ResponseEntity.ok(toDTO(prestataire)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PrestataireDTO>> getAllPrestataires() {
        List<PrestataireDTO> prestataires = prestataireRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestataires);
    }

    // Filtrer par localisation (exacte)
    @GetMapping("/search/localisation/{localisation}")
    public ResponseEntity<List<PrestataireDTO>> getPrestatairesByLocalisation(@PathVariable String localisation) {
        List<PrestataireDTO> prestataires = prestataireRepository.findByLocalisation(localisation)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestataires);
    }

    // Filtrer par localisation (partielle, insensible à la casse)
    @GetMapping("/search/localisation-containing/{localisation}")
    public ResponseEntity<List<PrestataireDTO>> getPrestatairesByLocalisationContaining(@PathVariable String localisation) {
        List<PrestataireDTO> prestataires = prestataireRepository.findByLocalisationContainingIgnoreCase(localisation)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestataires);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestataireDTO> updatePrestataire(@PathVariable Long id, @Valid @RequestBody Prestataire prestataire) {
        return prestataireRepository.findById(id)
                .map(existingPrestataire -> {
                    existingPrestataire.setNom(prestataire.getNom());
                    existingPrestataire.setLocalisation(prestataire.getLocalisation());
                    existingPrestataire.setContact(prestataire.getContact());
                    Prestataire updated = prestataireRepository.save(existingPrestataire);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePrestataire(@PathVariable Long id) {
        return prestataireRepository.findById(id)
                .map(existingPrestataire -> {
                    prestataireRepository.delete(existingPrestataire);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> countPrestataires() {
        long count = prestataireRepository.count();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsPrestataireById(@PathVariable Long id) {
        boolean exists = prestataireRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }
}