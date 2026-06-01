package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.MarqueDao;
import com.mns.cda.locmns.dto.CreateMarqueDto;
import com.mns.cda.locmns.dto.UpdateMarqueDto;
import com.mns.cda.locmns.model.Marque;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarqueService {
    private final MarqueDao marqueDao;

    // CREATE
    public Marque create(CreateMarqueDto dto) {
        Marque u = new Marque();
        
        u.setNom(dto.getNom());
        

        return marqueDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateMarqueDto dto) {
        Marque u = marqueDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque non trouvé"));
        
        u.setNom(dto.getNom());
        marqueDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!marqueDao.existsById(id)) {
            throw new RuntimeException("Marque non trouvé");
        }
        marqueDao.deleteById(id);
    }

    // GET ONE
    public Marque getById(int id) {
        return marqueDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque non trouvé"));
    }

    // GET ALL
    public java.util.List<Marque> getAll() {
        return marqueDao.findAll();
    }
}
