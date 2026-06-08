package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.ModeleDao;
import com.mns.cda.locmns.dto.CatalogueModeleDto;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.security.UtilisateurDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ModeleService {
    private final ModeleDao modeleDao;
    private final MaterielService materielService;

    // CREATE
    public Modele create(CreateModeleDto dto) {
        Modele u = new Modele();
        
        u.setNom(dto.getNom());
        u.setDescription(dto.getDescription());
        

        return modeleDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateModeleDto dto) {
        Modele u = modeleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Modele non trouvé"));
        
        u.setNom(dto.getNom());
        u.setDescription((dto.getDescription()));
        modeleDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!modeleDao.existsById(id)) {
            throw new RuntimeException("Modele non trouvé");
        }
        modeleDao.deleteById(id);
    }

    // GET ONE
    public Modele getById(int id) {
        return modeleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Modele non trouvé"));
    }

    // GET ALL
    public java.util.List<Modele> getAll() {
        return modeleDao.findAll();
    }

    public int calculerStockDisponible(Modele modele) {

        return modeleDao.calculerNonHsStockDisponible(modele.getId());
    }


    public Stream<Map.Entry<Modele, Integer>> getModelDisponible() {

        return modeleDao.findAll()
                .stream()
                .map(modele -> Map.entry(modele, calculerStockDisponible(modele)));
    }


    public List<CatalogueModeleDto> getCatalogue(
            String type,
            String marque,
            Boolean disponible
    ) {

        return getModelDisponible()
                .filter(entry -> {
                    Modele modele = entry.getKey();
                    if (type != null &&
                            !modele.getType().getNom().equals(type)) {
                        return false;
                    }
                    return true;
                })
                .filter(entry -> {
                    Modele modele = entry.getKey();
                    if (marque != null &&
                            !modele.getMarque().getNom().equals(marque)) {
                        return false;
                    }

                    return true;
                })

                .filter(entry -> {
                    if (Boolean.TRUE.equals(disponible)) {
                        return entry.getValue() > 0;
                    }
                    return true;
                })
                .map(entry ->
                        toCatalogueDto(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .toList();
    }

    private CatalogueModeleDto toCatalogueDto(Modele modele, int stock) {

        CatalogueModeleDto dto = new CatalogueModeleDto();

        dto.setId(modele.getId());
        dto.setNom(modele.getNom());
        dto.setDescription(modele.getDescription());
        dto.setImage(modele.getImage());
        dto.setStockDisponible(stock);
        dto.setEstDisponible(stock > 0);

        return dto;
    }
}
