package com.mns.cda.locmns.integration;

import com.mns.cda.locmns.dao.RoleDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.exception.EmailDejaUtiliseException;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.model.RoleNom;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.service.UtilisateurService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
@Transactional
class UtilisateurIntegrationTest {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void creerRoleParDefaut() {
        if (roleDao.findByRole(RoleNom.DEFAULT).isEmpty()) {
            Role role = new Role();
            role.setRole(RoleNom.DEFAULT);
            roleDao.save(role);
        }
    }

    @Test
    void devraitCreerEtRelireUnUtilisateurEnBase() {
        CreateUtilisateurDto dto = new CreateUtilisateurDto();
        dto.setEmail("integration.utilisateur@mns.fr");
        dto.setPassword("MotDePasse123!");
        dto.setNom("Integration");
        dto.setPrenom("Utilisateur");
        dto.setDateDeNaissance(LocalDate.of(1995, 5, 15));

        Utilisateur cree = utilisateurService.create(dto);
        entityManager.flush();
        entityManager.clear();

        Utilisateur enBase = utilisateurDao.findByEmailWithRoles(dto.getEmail()).orElseThrow();

        assertNotNull(cree.getId());
        assertEquals(dto.getEmail(), enBase.getEmail());
        assertEquals(dto.getNom(), enBase.getNom());
        assertEquals(dto.getPrenom(), enBase.getPrenom());
        assertNotEquals(dto.getPassword(), enBase.getPassword());
        assertTrue(passwordEncoder.matches(dto.getPassword(), enBase.getPassword()));
        assertTrue(enBase.getRoles().stream()
                .anyMatch(role -> role.getRole() == RoleNom.DEFAULT));
    }

    @Test
    void devraitModifierPuisSupprimerUnUtilisateurEnBase() {
        Utilisateur utilisateur = utilisateurService.create(creerUtilisateurDto(
                "avant.modification@mns.fr",
                "Avant",
                "Modification"
        ));

        UpdateUtilisateurDto modification = new UpdateUtilisateurDto();
        modification.setEmail("apres.modification@mns.fr");
        modification.setPassword("NouveauMotDePasse123!");
        modification.setNom("Apres");
        modification.setPrenom("Modification");
        modification.setDateDeNaissance(LocalDate.of(1992, 2, 20));

        utilisateurService.update(utilisateur.getId(), modification);
        entityManager.flush();
        entityManager.clear();

        Utilisateur modifie = utilisateurDao.findById(utilisateur.getId()).orElseThrow();
        assertEquals(modification.getEmail(), modifie.getEmail());
        assertEquals(modification.getNom(), modifie.getNom());
        assertTrue(passwordEncoder.matches(modification.getPassword(), modifie.getPassword()));

        utilisateurService.delete(modifie.getId());
        entityManager.flush();

        assertFalse(utilisateurDao.existsById(modifie.getId()));
    }

    @Test
    void devraitRefuserLaCreationAvecUnEmailDejaUtilise() {
        CreateUtilisateurDto dto = creerUtilisateurDto(
                "email.unique@mns.fr",
                "Premier",
                "Utilisateur"
        );
        utilisateurService.create(dto);
        entityManager.flush();

        assertThrows(
                EmailDejaUtiliseException.class,
                () -> utilisateurService.create(dto)
        );
    }

    private CreateUtilisateurDto creerUtilisateurDto(String email, String nom, String prenom) {
        CreateUtilisateurDto dto = new CreateUtilisateurDto();
        dto.setEmail(email);
        dto.setPassword("MotDePasse123!");
        dto.setNom(nom);
        dto.setPrenom(prenom);
        dto.setDateDeNaissance(LocalDate.of(1990, 1, 1));
        return dto;
    }
}
