package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.TypeDao;
import com.mns.cda.locmns.dto.CreateTypeDto;
import com.mns.cda.locmns.dto.UpdateTypeDto;
import com.mns.cda.locmns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeDao typeDao;

    // CREATE
    public Type create(CreateTypeDto dto) {
        Type u = new Type();

        u.setNom(dto.getNom());


        return typeDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateTypeDto dto) {
        Type u = typeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Type non trouvé"));

        u.setNom(dto.getNom());
        typeDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!typeDao.existsById(id)) {
            throw new RuntimeException("Type non trouvé");
        }
        typeDao.deleteById(id);
    }

    // GET ONE
    public Type getById(int id) {
        return typeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Type non trouvé"));
    }

    // GET ALL
    public java.util.List<Type> getAll() {
        return typeDao.findAll();
    }
}
