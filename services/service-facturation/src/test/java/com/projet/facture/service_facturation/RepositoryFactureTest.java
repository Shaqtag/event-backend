package com.projet.facture.service_facturation;

import com.projet.facture.service_facturation.modele.Facture;
import com.projet.facture.service_facturation.modele.StatutFacture;
import com.projet.facture.service_facturation.repository.RepositoryFacture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepositoryFactureTest {

    @Autowired
    private RepositoryFacture repositoryFacture;

    @Test
    void findByIdentifiantClient_shouldReturnFacturesForGivenClient() {
        // Création de factures en base
        Facture f1 = new Facture();
        f1.setIdentifiantClient("client1");
        f1.setMontant(100.0);
        f1.setStatut(StatutFacture.EN_ATTENTE);
        f1.setIdentifiantsPrestations(List.of("presta1"));
        repositoryFacture.save(f1);

        Facture f2 = new Facture();
        f2.setIdentifiantClient("client1");
        f2.setMontant(200.0);
        f2.setStatut(StatutFacture.PAYEE);
        f2.setIdentifiantsPrestations(List.of("presta2"));
        repositoryFacture.save(f2);

        Facture f3 = new Facture();
        f3.setIdentifiantClient("client2");
        f3.setMontant(300.0);
        f3.setStatut(StatutFacture.EN_ATTENTE);
        f3.setIdentifiantsPrestations(List.of("presta3"));
        repositoryFacture.save(f3);

        // Test méthode findByIdentifiantClient
        List<Facture> facturesClient1 = repositoryFacture.findByIdentifiantClient("client1");

        assertThat(facturesClient1).hasSize(2);
        assertThat(facturesClient1).allMatch(f -> f.getIdentifiantClient().equals("client1"));
    }
}
