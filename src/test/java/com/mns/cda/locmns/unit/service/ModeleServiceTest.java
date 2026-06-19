package com.mns.cda.locmns.unit.service;

import com.mns.cda.locmns.dao.ModeleDao;
import com.mns.cda.locmns.dto.CatalogueModeleDto;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.service.MaterielService;
import com.mns.cda.locmns.service.ModeleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModeleServiceTest {

    @Mock
    private ModeleDao modeleDao;

    @Mock
    private MaterielService materielService;

    @InjectMocks
    private ModeleService modeleService;

    @Test
    void devraitCreerModele() {
        CreateModeleDto dto = new CreateModeleDto();
        dto.setNom("iPhone");
        dto.setDescription("Desc");

        when(modeleDao.save(any())).thenAnswer(i -> i.getArgument(0));

        Modele result = modeleService.create(dto);

        assertEquals("iPhone", result.getNom());
        assertEquals("Desc", result.getDescription());

        verify(modeleDao).save(any(Modele.class));
    }

    @Test
    void devraitMettreAJourModele() {
        Modele modele = new Modele();
        modele.setId(1);

        UpdateModeleDto dto = new UpdateModeleDto();
        dto.setNom("Samsung");
        dto.setDescription("New Desc");

        when(modeleDao.findById(1)).thenReturn(Optional.of(modele));

        modeleService.update(1, dto);

        assertEquals("Samsung", modele.getNom());
        assertEquals("New Desc", modele.getDescription());

        verify(modeleDao).save(modele);
    }

    @Test
    void devraitLeverExceptionSiModeleUpdateIntrouvable() {
        when(modeleDao.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> modeleService.update(1, new UpdateModeleDto())
        );

        assertEquals("Modele non trouvé", ex.getMessage());
    }

    @Test
    void devraitSupprimerModele() {
        when(modeleDao.existsById(1)).thenReturn(true);

        modeleService.delete(1);

        verify(modeleDao).deleteById(1);
    }

    @Test
    void devraitLeverExceptionSiModeleSuppressionIntrouvable() {
        when(modeleDao.existsById(1)).thenReturn(false);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> modeleService.delete(1)
        );

        assertEquals("Modele non trouvé", ex.getMessage());
    }

    @Test
    void devraitRetournerModeleParId() {
        Modele modele = new Modele();
        modele.setId(1);

        when(modeleDao.findById(1)).thenReturn(Optional.of(modele));

        Modele result = modeleService.getById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void devraitRetournerTousLesModeles() {
        when(modeleDao.findAll()).thenReturn(List.of(new Modele(), new Modele()));

        List<Modele> result = modeleService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void devraitConstruireCatalogueAvecStockEtDisponibilite() {
        // mock SQL DTO (résultat requête custom)
        var sqlDto = new com.mns.cda.locmns.dto.CatalogueSqlModeleDto() {
            public Integer getId() { return 1; }
            public String getNom() { return "iPhone"; }
            public String getDescription() { return "Desc"; }
            public String getImage() { return "img.png"; }
        };

        when(modeleDao.getCatalogue(null, null, null))
                .thenReturn(List.of(sqlDto));

        when(modeleDao.compteStockDisponible(1)).thenReturn(3L);

        List<CatalogueModeleDto> result =
                modeleService.getCatalogue(null, null, null);

        assertEquals(1, result.size());

        CatalogueModeleDto dto = result.get(0);

        assertEquals(1, dto.getId());
        assertEquals("iPhone", dto.getNom());
        assertEquals("Desc", dto.getDescription());
        assertEquals("img.png", dto.getImage());
        assertEquals(3L, dto.getStockDisponible());
        assertTrue(dto.isEstDisponible());
    }

    @Test
    void devraitMarquerModeleIndisponibleSiStockZero() {
        var sqlDto = new com.mns.cda.locmns.dto.CatalogueSqlModeleDto() {
            public Integer getId() { return 1; }
            public String getNom() { return "iPhone"; }
            public String getDescription() { return "Desc"; }
            public String getImage() { return "img.png"; }
        };

        when(modeleDao.getCatalogue(null, null, null))
                .thenReturn(List.of(sqlDto));

        when(modeleDao.compteStockDisponible(1)).thenReturn(0L);

        List<CatalogueModeleDto> result =
                modeleService.getCatalogue(null, null, null);

        assertFalse(result.get(0).isEstDisponible());
    }
}