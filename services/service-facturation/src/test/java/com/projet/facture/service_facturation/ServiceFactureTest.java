package com.projet.facture.service_facturation;

import com.projet.facture.service_facturation.modele.Facture;
import com.projet.facture.service_facturation.modele.StatutFacture;
import com.projet.facture.service_facturation.repository.RepositoryFacture;
import com.projet.facture.service_facturation.service.ServiceFacture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceFactureTest {

    @InjectMocks
    private ServiceFacture serviceFacture;

    @Mock
    private RepositoryFacture repositoryFacture;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creerFacture_valide_sauvegarde() {
        Facture facture = new Facture();
        facture.setIdentifiantClient("client1");
        facture.setMontant(100.0);
        facture.setIdentifiantsPrestations(Arrays.asList("presta1", "presta2"));

        when(repositoryFacture.save(facture)).thenReturn(facture);

        Facture resultat = serviceFacture.creerFacture(facture);

        assertNotNull(resultat);
        verify(repositoryFacture, times(1)).save(facture);
    }

    @Test
    void creerFacture_champsManquants_IllegalArgumentException() {
        Facture facture = new Facture();
        facture.setMontant(100.0);
        facture.setIdentifiantsPrestations(Arrays.asList("presta1"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceFacture.creerFacture(facture));
        assertEquals("Champs requis manquants : identifiantClient, montant, ou identifiantsPrestations", exception.getMessage());
    }

    @Test
    void obtenirToutesFactures_sansClient_retourneToutes() {
        List<Facture> factures = List.of(new Facture(), new Facture());
        when(repositoryFacture.findAll()).thenReturn(factures);

        List<Facture> resultat = serviceFacture.obtenirToutesFactures(null);

        assertEquals(2, resultat.size());
        verify(repositoryFacture, times(1)).findAll();
    }

    @Test
    void obtenirToutesFactures_avecClient_retourneFiltre() {
        List<Facture> factures = List.of(new Facture());
        when(repositoryFacture.findByIdentifiantClient("client1")).thenReturn(factures);

        List<Facture> resultat = serviceFacture.obtenirToutesFactures("client1");

        assertEquals(1, resultat.size());
        verify(repositoryFacture, times(1)).findByIdentifiantClient("client1");
    }

    @Test
    void mettreAJourStatutFacture_factureExistante_miseAJour() {
        Facture facture = new Facture();
        facture.setIdentifiant(1L);  // Correction ici
        facture.setStatut(StatutFacture.EN_ATTENTE);

        when(repositoryFacture.findById(1L)).thenReturn(Optional.of(facture));
        when(repositoryFacture.save(any(Facture.class))).thenAnswer(i -> i.getArgument(0));

        Facture resultat = serviceFacture.mettreAJourStatutFacture(1L, StatutFacture.PAYEE);

        assertEquals(StatutFacture.PAYEE, resultat.getStatut());
        verify(repositoryFacture, times(1)).save(facture);
    }

    @Test
    void mettreAJourStatutFacture_factureNonTrouvee_IllegalArgumentException() {
        when(repositoryFacture.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceFacture.mettreAJourStatutFacture(1L, StatutFacture.PAYEE));
        assertEquals("Facture non trouvée", exception.getMessage());
    }

    @Test
    void supprimerFacture_factureExistante_suppression() {
        when(repositoryFacture.existsById(1L)).thenReturn(true);
        doNothing().when(repositoryFacture).deleteById(1L);

        serviceFacture.supprimerFacture(1L);

        verify(repositoryFacture, times(1)).deleteById(1L);
    }

    @Test
    void supprimerFacture_factureNonTrouvee_IllegalArgumentException() {
        when(repositoryFacture.existsById(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serviceFacture.supprimerFacture(1L));
        assertEquals("Facture non trouvée", exception.getMessage());
    }
}
