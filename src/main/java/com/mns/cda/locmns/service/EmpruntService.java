package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.model.Emprunt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpruntService {
    private final EmpruntDao empruntDao;

    // CREATE
    public Emprunt create(CreateEmpruntDto dto) {
        Emprunt u = new Emprunt();
        
        u.setDateDebutEmprunt(dto.getDateDebutEmprunt());
        u.setDateRetourEmpruntPrevisionelle(dto.getDateRetourEmpruntPrevisionelle());
        

        return empruntDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateEmpruntDto dto) {
        Emprunt u = empruntDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        u.setDateRetourEmpruntReelle(dto.getDateRetourEmpruntReelle());
        empruntDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!empruntDao.existsById(id)) {
            throw new RuntimeException("Emprunt non trouvé");
        }
        empruntDao.deleteById(id);
    }

    // GET ONE
    public Emprunt getById(int id) {
        return empruntDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));
    }

    // GET ALL
    public java.util.List<Emprunt> getAll() {
        return empruntDao.findAll();
    }
}
