package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.DocumentationDao;
import com.mns.cda.locmns.dto.CreateDocumentationDto;
import com.mns.cda.locmns.dto.UpdateDocumentationDto;
import com.mns.cda.locmns.model.Documentation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentationService {
    private final DocumentationDao documentationDao;

    // CREATE
    public Documentation create(CreateDocumentationDto dto) {
        Documentation u = new Documentation();
        
        u.setDescription(dto.getDescription());
        

        return documentationDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateDocumentationDto dto) {
        Documentation u = documentationDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentation non trouvé"));
        
        u.setDescription(dto.getDescription());
        documentationDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!documentationDao.existsById(id)) {
            throw new RuntimeException("Documentation non trouvé");
        }
        documentationDao.deleteById(id);
    }

    // GET ONE
    public Documentation getById(int id) {
        return documentationDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentation non trouvé"));
    }

    // GET ALL
    public java.util.List<Documentation> getAll() {
        return documentationDao.findAll();
    }
}
