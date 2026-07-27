package com.mns.cda.locmns.integration;

import com.mns.cda.locmns.dao.ModeleDao;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.service.ModeleService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
@Transactional
class ModeleIntegrationTest {

    @Autowired
    private ModeleService modeleService;

    @Autowired
    private ModeleDao modeleDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    void devraitCreerEtRelireUnModeleEnBase() {
        CreateModeleDto dto = new CreateModeleDto();
        dto.setNom("Modele integration");
        dto.setDescription("Modele cree par un test d'integration");
        dto.setImage("modele-integration.webp");

        Modele cree = modeleService.create(dto);
        entityManager.flush();
        entityManager.clear();

        Modele enBase = modeleDao.findById(cree.getId()).orElseThrow();

        assertNotNull(cree.getId());
        assertEquals(dto.getNom(), enBase.getNom());
        assertEquals(dto.getDescription(), enBase.getDescription());
    }

    @Test
    void devraitModifierPuisSupprimerUnModeleEnBase() {
        CreateModeleDto creation = new CreateModeleDto();
        creation.setNom("Ancien modele");
        creation.setDescription("Ancienne description");
        creation.setImage("ancien.webp");
        Modele modele = modeleService.create(creation);

        UpdateModeleDto modification = new UpdateModeleDto();
        modification.setNom("Nouveau modele");
        modification.setDescription("Nouvelle description");
        modification.setImage("nouveau.webp");

        modeleService.update(modele.getId(), modification);
        entityManager.flush();
        entityManager.clear();

        Modele modifie = modeleDao.findById(modele.getId()).orElseThrow();
        assertEquals(modification.getNom(), modifie.getNom());
        assertEquals(modification.getDescription(), modifie.getDescription());

        modeleService.delete(modifie.getId());
        entityManager.flush();

        assertFalse(modeleDao.existsById(modifie.getId()));
    }
}
