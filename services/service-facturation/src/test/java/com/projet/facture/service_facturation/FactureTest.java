package com.projet.facture.service_facturation;

import org.junit.jupiter.api.Test;

import com.projet.facture.service_facturation.modele.Facture;
import com.projet.facture.service_facturation.modele.StatutFacture;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FactureTest {

    @Test
    void testCreationFacture() {
        Facture facture = new Facture();

        facture.setIdentifiant(1L);
        facture.setIdentifiantClient("client123");
        facture.setMontant(250.50);
        facture.setStatut(StatutFacture.PAYEE);
        LocalDateTime now = LocalDateTime.now();
        facture.setDateCreation(now);
        facture.setIdentifiantsPrestations(List.of("presta1", "presta2"));

        assertEquals(1L, facture.getIdentifiant());
        assertEquals("client123", facture.getIdentifiantClient());
        assertEquals(250.50, facture.getMontant());
        assertEquals(StatutFacture.PAYEE, facture.getStatut());
        assertEquals(now, facture.getDateCreation());
        assertEquals(List.of("presta1", "presta2"), facture.getIdentifiantsPrestations());
    }

    @Test
    void testValeursParDefaut() {
        Facture facture = new Facture();

        assertNotNull(facture.getDateCreation());
        assertEquals(StatutFacture.EN_ATTENTE, facture.getStatut());
    }
}
