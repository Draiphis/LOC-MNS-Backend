package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dto.CreateMaterielDto;
import com.mns.cda.locmns.dto.MaterielDisponibleDto;
import com.mns.cda.locmns.dto.UpdateMaterielDto;
import com.mns.cda.locmns.model.Etat;
import com.mns.cda.locmns.model.EtatUsure;
import com.mns.cda.locmns.model.Materiel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterielService {


    private final EtatMaterielService etatMaterielService;

    private final MaterielDao materielDao;

    // CREATE
    public Materiel create(CreateMaterielDto dto) {
        Materiel u = new Materiel();
        
        u.setReference(dto.getReference());
        

        return materielDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateMaterielDto dto) {
        Materiel u = materielDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Materiel non trouvé"));
        
        u.setReference(dto.getReference());
        materielDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!materielDao.existsById(id)) {
            throw new RuntimeException("Materiel non trouvé");
        }
        materielDao.deleteById(id);
    }

    // GET ONE
    public Materiel getById(int id) {
        return materielDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Materiel non trouvé"));
    }

    // GET ALL
    public java.util.List<Materiel> getAll() {
        return materielDao.findAll();
    }

    public boolean estDisponible(Integer modeleId) {
        return materielDao.auMoinsUnMaterielDisponible(modeleId);

    }

    public long getStockDisponible(Integer modeleId) {
        return materielDao.stockDisponibleParModeleId(modeleId);
    }

    public List<MaterielDisponibleDto> getDisponibles(Integer modeleId) {

        return materielDao.findDisponiblesByModeleId(modeleId)
                .stream()
                .map(m -> {
                    MaterielDisponibleDto dto = new MaterielDisponibleDto();

                    dto.setId(m.getId());
                    dto.setReference(m.getReference());

                    dto.setModeleId(m.getModele().getId());
                    dto.setModeleNom(m.getModele().getNom());

                    return dto;
                })
                .toList();
    }


}
