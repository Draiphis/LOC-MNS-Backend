package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpruntService {
    private final EmpruntDao empruntDao;
    private final MaterielDao materielDao;
    private final UtilisateurDao utilisateurDao;

    public Emprunt create(CreateEmpruntDto dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        Utilisateur utilisateur = utilisateurDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Materiel materiel = materielDao.findById(dto.getMaterielId())
                .orElseThrow(() -> new RuntimeException("Matériel introuvable"));


        Emprunt emprunt = new Emprunt();

        emprunt.setDateDebutEmprunt(dto.getDateDebutEmprunt());
        emprunt.setDateRetourEmpruntPrevisionelle(dto.getDateRetourEmpruntPrevisionelle());

        emprunt.setMateriel(materiel);
        emprunt.setDemandeur(utilisateur);

        return empruntDao.save(emprunt);
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
