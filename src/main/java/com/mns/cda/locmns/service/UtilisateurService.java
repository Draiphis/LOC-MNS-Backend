package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.RoleDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.exception.EmailDejaUtiliseException;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.model.RoleNom;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurDao utilisateurDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    // CREATE
    public Utilisateur create(CreateUtilisateurDto dto) {
        if (utilisateurDao.existsByEmail(dto.getEmail())) {
            throw new EmailDejaUtiliseException(
                    "Un utilisateur possède déjà l'adresse e-mail " + dto.getEmail() + "."
            );
        }

        Utilisateur u = new Utilisateur();


        u.setId(null);
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setDateDeNaissance(dto.getDateDeNaissance());

        Role defaultRole = roleDao.findByRole(RoleNom.DEFAULT)
                .orElseThrow(() -> new RuntimeException("Role DEFAULT introuvable"));


        u.setRoles(Set.of(defaultRole));

        return utilisateurDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateUtilisateurDto dto) {
        Utilisateur u = utilisateurDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
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

    public Utilisateur insert(Utilisateur utilisateur){
        utilisateur.setId(null);
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

        utilisateurDao.save(utilisateur);

        return utilisateur;
    }
}
