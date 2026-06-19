package com.mns.cda.locmns.unit.model;

import com.mns.cda.locmns.model.Emprunt;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmpruntTest {

    private static Validator validator;

    @BeforeAll
    static void initialiserLeValidateur() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Emprunt creerEmpruntValide() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateDemandeEmprunt(LocalDateTime.now());
        emprunt.setDateDebutEmprunt(LocalDate.now());
        emprunt.setDateRetourEmpruntPrevisionelle(LocalDate.now().plusDays(7));
        emprunt.setDateRetourEmpruntReelle(LocalDate.now().plusDays(7));
        return emprunt;
    }

    @Test
    void devraitEtreValideQuandToutesLesDonneesSontCorrectes() {
        Emprunt emprunt = creerEmpruntValide();

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(violations.isEmpty());
    }

    @Test
    void devraitEchouerQuandLaDateDeDemandeEstNulle() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateDemandeEmprunt(null);

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateDemandeEmprunt".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeDebutEstNulle() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateDebutEmprunt(null);

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateDebutEmprunt".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeDebutEstDansLePasse() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateDebutEmprunt(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateDebutEmprunt".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeRetourPrevisionnelleEstNulle() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateRetourEmpruntPrevisionelle(null);

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateRetourEmpruntPrevisionelle".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeRetourPrevisionnelleEstDansLePasse() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateRetourEmpruntPrevisionelle(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateRetourEmpruntPrevisionelle".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDateDeRetourReelleEstDansLePasse() {
        Emprunt emprunt = creerEmpruntValide();
        emprunt.setDateRetourEmpruntReelle(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Emprunt>> violations = validator.validate(emprunt);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "dateRetourEmpruntReelle".equals(v.getPropertyPath().toString()))
        );
    }
}

