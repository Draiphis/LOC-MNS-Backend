package com.mns.cda.locmns.unit.service;

import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.EmpruntReponseDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.exception.DatesEmpruntAbsentesException;
import com.mns.cda.locmns.exception.DatesEmpruntInvalidesException;
import com.mns.cda.locmns.exception.AucunMaterielDisponibleException;
import com.mns.cda.locmns.model.*;
import com.mns.cda.locmns.service.EmpruntService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpruntServiceTest {

    @Mock
    private EmpruntDao empruntDao;

    @Mock
    private MaterielDao materielDao;

    @Mock
    private UtilisateurDao utilisateurDao;

    @InjectMocks
    private EmpruntService empruntService;

    private void mockSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@mail.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    private CreateEmpruntDto creerDtoValide() {
        CreateEmpruntDto dto = new CreateEmpruntDto();
        dto.setModeleId(1);
        dto.setDateDebutEmprunt(LocalDate.now().plusDays(1));
        dto.setDateRetourEmpruntPrevisionelle(LocalDate.now().plusDays(5));
        return dto;
    }

    @Test
    void devraitCreerEmpruntAvecMaterielEtUtilisateur() {
        mockSecurityContext();

        CreateEmpruntDto dto = creerDtoValide();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@mail.com");

        Materiel materiel = new Materiel();
        materiel.setId(1);
        materiel.setReference("REF123");

        when(utilisateurDao.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(utilisateur));

        when(materielDao.findDisponiblesPourPeriode(
                1,
                dto.getDateDebutEmprunt(),
                dto.getDateRetourEmpruntPrevisionelle(),
                StatutEmprunt.REFUSE
        ))
                .thenReturn(List.of(materiel));

        when(empruntDao.save(any())).thenAnswer(i -> i.getArgument(0));

        Emprunt result = empruntService.create(dto);

        assertNotNull(result);
        assertEquals(utilisateur, result.getDemandeur());
        assertEquals(materiel, result.getMateriel());
        assertEquals(StatutEmprunt.EN_ATTENTE, result.getStatut());
    }

    @Test
    void devraitRefuserUnePeriodeSansMaterielDisponible() {
        mockSecurityContext();
        CreateEmpruntDto dto = creerDtoValide();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@mail.com");
        when(utilisateurDao.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(utilisateur));
        when(materielDao.findDisponiblesPourPeriode(
                1,
                dto.getDateDebutEmprunt(),
                dto.getDateRetourEmpruntPrevisionelle(),
                StatutEmprunt.REFUSE
        )).thenReturn(List.of());

        assertThrows(
                AucunMaterielDisponibleException.class,
                () -> empruntService.create(dto)
        );
    }

    @Test
    void devraitLeverExceptionSiDatesAbsentes() {
        CreateEmpruntDto dto = new CreateEmpruntDto();

        DatesEmpruntAbsentesException ex = assertThrows(
                DatesEmpruntAbsentesException.class,
                () -> empruntService.create(dto)
        );

        assertTrue(ex.getMessage().contains("obligatoires"));
    }

    @Test
    void devraitLeverExceptionSiDatesInvalides() {
        CreateEmpruntDto dto = creerDtoValide();
        dto.setDateDebutEmprunt(LocalDate.now().plusDays(5));
        dto.setDateRetourEmpruntPrevisionelle(LocalDate.now().plusDays(1));

        DatesEmpruntInvalidesException ex = assertThrows(
                DatesEmpruntInvalidesException.class,
                () -> empruntService.create(dto)
        );

        assertTrue(ex.getMessage().contains("doit être antérieure"));
    }

    @Test
    void devraitLeverExceptionSiUtilisateurIntrouvable() {
        mockSecurityContext();

        CreateEmpruntDto dto = creerDtoValide();

        when(utilisateurDao.findByEmail("test@mail.com"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empruntService.create(dto)
        );

        assertEquals("Utilisateur introuvable", ex.getMessage());
    }

    @Test
    void devraitMettreAJourDateRetourReelle() {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1);

        UpdateEmpruntDto dto = new UpdateEmpruntDto();
        dto.setDateRetourEmpruntReelle(LocalDate.now());

        when(empruntDao.findById(1)).thenReturn(Optional.of(emprunt));

        empruntService.update(1, dto);

        assertEquals(dto.getDateRetourEmpruntReelle(), emprunt.getDateRetourEmpruntReelle());
        verify(empruntDao).save(emprunt);
    }

    @Test
    void devraitSupprimerEmpruntSiExiste() {
        when(empruntDao.existsById(1)).thenReturn(true);

        empruntService.delete(1);

        verify(empruntDao).deleteById(1);
    }

    @Test
    void devraitLeverExceptionSiSuppressionEmpruntInexistant() {
        when(empruntDao.existsById(1)).thenReturn(false);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> empruntService.delete(1)
        );

        assertEquals("Emprunt non trouvé", ex.getMessage());
    }

    @Test
    void devraitValiderEmprunt() {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1);

        when(empruntDao.findById(1)).thenReturn(Optional.of(emprunt));

        empruntService.valider(1);

        assertEquals(StatutEmprunt.APPROUVE, emprunt.getStatut());
        verify(empruntDao).save(emprunt);
    }

    @Test
    void devraitRefuserEmprunt() {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1);

        when(empruntDao.findById(1)).thenReturn(Optional.of(emprunt));

        empruntService.refuser(1);

        assertEquals(StatutEmprunt.REFUSE, emprunt.getStatut());
        verify(empruntDao).save(emprunt);
    }

    @Test
    void devraitRetournerEmpruntParId() {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1);

        when(empruntDao.findById(1)).thenReturn(Optional.of(emprunt));

        Emprunt result = empruntService.getById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void devraitRetournerUniquementLesEmpruntsDeUtilisateurConnecte() {
        mockSecurityContext();

        Modele modele = new Modele();
        modele.setNom("Dell Latitude");
        modele.setImage("latitude.jpg");

        Materiel materiel = new Materiel();
        materiel.setId(12);
        materiel.setReference("PC-012");
        materiel.setModele(modele);

        Utilisateur demandeur = new Utilisateur();
        demandeur.setNom("Dupont");
        demandeur.setPrenom("Alice");

        Emprunt emprunt = new Emprunt();
        emprunt.setId(4);
        emprunt.setStatut(StatutEmprunt.EN_ATTENTE);
        emprunt.setMateriel(materiel);
        emprunt.setDemandeur(demandeur);
        emprunt.setDateDebutEmprunt(LocalDate.now().plusDays(1));
        emprunt.setDateRetourEmpruntPrevisionelle(LocalDate.now().plusDays(3));

        when(empruntDao.findByDemandeurEmailOrderByDateDemandeEmpruntDesc("test@mail.com"))
                .thenReturn(List.of(emprunt));

        List<EmpruntReponseDto> resultat = empruntService.getMesEmprunts();

        assertEquals(1, resultat.size());
        assertEquals("Dell Latitude", resultat.get(0).getModeleNom());
        assertEquals("PC-012", resultat.get(0).getMaterielNom());
        verify(empruntDao).findByDemandeurEmailOrderByDateDemandeEmpruntDesc("test@mail.com");
        verify(empruntDao, never()).findAll();
    }
}
