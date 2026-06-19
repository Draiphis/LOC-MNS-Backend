package com.mns.cda.locmns.unit.model;

import com.mns.cda.locmns.model.Modele;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ModeleTest {

    private static Validator validator;

    @BeforeAll
    static void initialiserValidateur() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Modele creerModeleValide() {
        Modele modele = new Modele();
        modele.setNom("iPhone 15");
        modele.setImage("image.png");
        modele.setDescription("Description valide du modèle");
        return modele;
    }

    @Test
    void devraitEtreValideQuandToutesLesDonneesSontCorrectes() {
        Modele modele = creerModeleValide();

        Set<ConstraintViolation<Modele>> violations = validator.validate(modele);

        assertTrue(violations.isEmpty());
    }

    @Test
    void devraitEchouerQuandLeNomEstVide() {
        Modele modele = creerModeleValide();
        modele.setNom("");

        Set<ConstraintViolation<Modele>> violations = validator.validate(modele);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "nom".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLeNomEstNull() {
        Modele modele = creerModeleValide();
        modele.setNom(null);

        Set<ConstraintViolation<Modele>> violations = validator.validate(modele);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "nom".equals(v.getPropertyPath().toString()))
        );
    }

    @Test
    void devraitEchouerQuandLaDescriptionEstTropLongue() {
        Modele modele = creerModeleValide();
        modele.setDescription("a".repeat(600));

        Set<ConstraintViolation<Modele>> violations = validator.validate(modele);

        assertTrue(
                violations.stream()
                        .anyMatch(v -> "description".equals(v.getPropertyPath().toString()))
        );
    }
}