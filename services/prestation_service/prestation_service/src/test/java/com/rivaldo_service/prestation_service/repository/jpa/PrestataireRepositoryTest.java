package com.rivaldo_service.prestation_service.repository.jpa;

import com.rivaldo_service.prestation_service.model.Prestataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrestataireRepositoryTest {

    @Autowired
    private PrestataireRepository prestataireRepository;

    private Prestataire prestataire;

    @BeforeEach
    void setUp() {
        prestataireRepository.deleteAll();
        prestataire = new Prestataire("John Doe", "Paris", "123456789", "password");
    }

    @Test
    void savePrestataire_shouldPersistPrestataire() {
        Prestataire saved = prestataireRepository.save(prestataire);

        assertNotNull(saved.getPrestataireId());
        assertEquals("John Doe", saved.getNom());
    }

    @Test
    void findById_shouldReturnPrestataire() {
        Prestataire saved = prestataireRepository.save(prestataire);

        Prestataire found = prestataireRepository.findById(saved.getPrestataireId()).orElse(null);

        assertNotNull(found);
        assertEquals("John Doe", found.getNom());
    }

    @Test
    void findAll_shouldReturnAllPrestataires() {
        prestataireRepository.save(prestataire);

        List<Prestataire> prestataires = prestataireRepository.findAll();

        assertEquals(1, prestataires.size());
        assertEquals("John Doe", prestataires.get(0).getNom());
    }

    @Test
    void findByLocalisation_shouldReturnMatchingPrestataires() {
        prestataireRepository.save(prestataire);

        List<Prestataire> prestataires = prestataireRepository.findByLocalisation("Paris");

        assertEquals(1, prestataires.size());
        assertEquals("John Doe", prestataires.get(0).getNom());
    }

    @Test
    void findByLocalisationContainingIgnoreCase_shouldReturnMatchingPrestataires() {
        prestataireRepository.save(prestataire);

        List<Prestataire> prestataires = prestataireRepository.findByLocalisationContainingIgnoreCase("par");

        assertEquals(1, prestataires.size());
        assertEquals("John Doe", prestataires.get(0).getNom());
    }

    @Test
    void findByNomContainingIgnoreCase_shouldReturnMatchingPrestataires() {
        prestataireRepository.save(prestataire);

        List<Prestataire> prestataires = prestataireRepository.findByNomContainingIgnoreCase("john");

        assertEquals(1, prestataires.size());
        assertEquals("John Doe", prestataires.get(0).getNom());
    }

    @Test
    void deleteById_shouldRemovePrestataire() {
        Prestataire saved = prestataireRepository.save(prestataire);

        prestataireRepository.deleteById(saved.getPrestataireId());
        Prestataire found = prestataireRepository.findById(saved.getPrestataireId()).orElse(null);

        assertNull(found);
    }

    @Test
    void count_shouldReturnCorrectCount() {
        prestataireRepository.save(prestataire);

        long count = prestataireRepository.count();

        assertEquals(1, count);
    }

    @Test
    void existsById_shouldReturnTrue() {
        Prestataire saved = prestataireRepository.save(prestataire);

        boolean exists = prestataireRepository.existsById(saved.getPrestataireId());

        assertTrue(exists);
    }
}