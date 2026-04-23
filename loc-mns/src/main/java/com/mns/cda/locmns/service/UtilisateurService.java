package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurDao utilisateurDao;

    // CREATE
    public Utilisateur create(CreateUtilisateurDto dto) {
        Utilisateur u = new Utilisateur();

        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateDeNaissance(dto.getDateDeNaissance());

        return utilisateurDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateUtilisateurDto dto) {
        Utilisateur u = utilisateurDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateDeNaissance(dto.getDateDeNaissance());

        utilisateurDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!utilisateurDao.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurDao.deleteById(id);
    }

    // GET ONE
    public Utilisateur getById(int id) {
        return utilisateurDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // GET ALL
    public java.util.List<Utilisateur> getAll() {
        return utilisateurDao.findAll();
    }
}
