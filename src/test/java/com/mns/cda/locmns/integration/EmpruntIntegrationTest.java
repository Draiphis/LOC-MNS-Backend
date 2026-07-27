package com.mns.cda.locmns.integration;

import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dao.ModeleDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.model.StatutEmprunt;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.service.EmpruntService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
@Transactional
class EmpruntIntegrationTest {

    private static final String EMAIL_DEMANDEUR = "demandeur.integration@mns.fr";

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private EmpruntDao empruntDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private ModeleDao modeleDao;

    @Autowired
    private MaterielDao materielDao;

    @Autowired
    private EntityManager entityManager;

    private Modele modele;
    private Materiel materiel;

    @BeforeEach
    void preparerDonneesEtAuthentification() {
        Utilisateur demandeur = new Utilisateur();
        demandeur.setEmail(EMAIL_DEMANDEUR);
        demandeur.setPassword("MotDePasseEncodePourLeTest");
        demandeur.setNom("Integration");
        demandeur.setPrenom("Demandeur");
        demandeur.setDateDeNaissance(LocalDate.of(1995, 1, 1));
        demandeur.setRoles(Collections.emptySet());
        utilisateurDao.save(demandeur);

        modele = new Modele();
        modele.setNom("Modele emprunt integration");
        modele.setDescription("Modele disponible pour le test");
        modele = modeleDao.save(modele);

        materiel = new Materiel();
        materiel.setReference("REF-INTEGRATION-001");
        materiel.setModele(modele);
        materiel = materielDao.save(materiel);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        EMAIL_DEMANDEUR,
                        null,
                        Collections.emptyList()
                )
        );
    }

    @AfterEach
    void nettoyerAuthentification() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void devraitCreerUnEmpruntEtPersisterSesRelations() {
        CreateEmpruntDto dto = creerDtoValide();

        Emprunt cree = empruntService.create(dto);
        entityManager.flush();
        entityManager.clear();

        Emprunt enBase = empruntDao.findById(cree.getId()).orElseThrow();

        assertNotNull(cree.getId());
        assertEquals(StatutEmprunt.EN_ATTENTE, enBase.getStatut());
        assertEquals(EMAIL_DEMANDEUR, enBase.getDemandeur().getEmail());
        assertEquals(materiel.getId(), enBase.getMateriel().getId());
        assertEquals(dto.getDateDebutEmprunt(), enBase.getDateDebutEmprunt());
        assertEquals(
                dto.getDateRetourEmpruntPrevisionelle(),
                enBase.getDateRetourEmpruntPrevisionelle()
        );
    }

    @Test
    void devraitValiderPuisRefuserUnEmpruntEnBase() {
        Emprunt emprunt = empruntService.create(creerDtoValide());
        entityManager.flush();

        empruntService.valider(emprunt.getId());
        entityManager.flush();
        entityManager.clear();

        Emprunt valide = empruntDao.findById(emprunt.getId()).orElseThrow();
        assertEquals(StatutEmprunt.APPROUVE, valide.getStatut());

        empruntService.refuser(valide.getId());
        entityManager.flush();
        entityManager.clear();

        Emprunt refuse = empruntDao.findById(emprunt.getId()).orElseThrow();
        assertEquals(StatutEmprunt.REFUSE, refuse.getStatut());
    }

    private CreateEmpruntDto creerDtoValide() {
        CreateEmpruntDto dto = new CreateEmpruntDto();
        dto.setModeleId(modele.getId());
        dto.setDateDebutEmprunt(LocalDate.now().plusDays(1));
        dto.setDateRetourEmpruntPrevisionelle(LocalDate.now().plusDays(5));
        return dto;
    }
}
