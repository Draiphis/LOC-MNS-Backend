package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.ModeleDao;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeleService {
    private final ModeleDao modeleDao;

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
}
