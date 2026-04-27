package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.EtatDao;
import com.mns.cda.locmns.dto.CreateEtatDto;
import com.mns.cda.locmns.dto.UpdateEtatDto;
import com.mns.cda.locmns.model.Etat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtatService {
    private final EtatDao etatDao;

    // CREATE
    public Etat create(CreateEtatDto dto) {
        Etat u = new Etat();
        
        u.setUsure(dto.getUsure());
        

        return etatDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateEtatDto dto) {
        Etat u = etatDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Etat non trouvé"));
        
        u.setUsure(dto.getUsure());
        etatDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!etatDao.existsById(id)) {
            throw new RuntimeException("Etat non trouvé");
        }
        etatDao.deleteById(id);
    }

    // GET ONE
    public Etat getById(int id) {
        return etatDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Etat non trouvé"));
    }

    // GET ALL
    public java.util.List<Etat> getAll() {
        return etatDao.findAll();
    }
}
