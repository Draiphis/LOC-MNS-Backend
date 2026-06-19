package com.mns.cda.locmns.unit.model;

import com.mns.cda.locmns.model.Utilisateur;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilisateurTest {

    private static Validator validator;

    @BeforeAll
    static void initialiserLeValidateur() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Utilisateur creerUtilisateurValide() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setPassword("MotDePasse123");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setDateDeNaissance(LocalDate.of(1995, 1, 1));
        return utilisateur;
    }

    @Test
    void devraitEtreValideQuandToutesLesDonneesSontCorrectes() {
        Utilisateur utilisateur = creerUtilisateurValide();

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(violations.isEmpty());
    }

    @Test
    void devraitEchouerQuandLEmailEstVide() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setEmail("");

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "email".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLEmailEstInvalide() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setEmail("email-invalide");

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "email".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLeMotDePasseEstTropCourt() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setPassword("court");

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "password".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLeNomEstVide() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setNom("");

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "nom".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLePrenomEstVide() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setPrenom("");

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "prenom".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeNaissanceEstNulle() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setDateDeNaissance(null);

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateDeNaissance".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeNaissanceEstDansLeFutur() {
        Utilisateur utilisateur = creerUtilisateurValide();
        utilisateur.setDateDeNaissance(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Utilisateur>> violations =
                validator.validate(utilisateur);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateDeNaissance".equals(v.getPropertyPath().toString()))
        );
    }
}