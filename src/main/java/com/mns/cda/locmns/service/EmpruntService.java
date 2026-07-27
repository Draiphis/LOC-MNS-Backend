package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.EmpruntReponseDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.exception.AucunMaterielDisponibleException;
import com.mns.cda.locmns.exception.DatesEmpruntAbsentesException;
import com.mns.cda.locmns.exception.DatesEmpruntInvalidesException;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.model.StatutEmprunt;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpruntService {
    private final EmpruntDao empruntDao;
    private final MaterielDao materielDao;
    private final UtilisateurDao utilisateurDao;

    public Emprunt create(CreateEmpruntDto dto) {

        if (dto.getDateDebutEmprunt() == null || dto.getDateRetourEmpruntPrevisionelle() == null) {
            throw new DatesEmpruntAbsentesException("Les dates de début et de retour sont obligatoires.");
        }

        if (!dto.getDateDebutEmprunt().isBefore(dto.getDateRetourEmpruntPrevisionelle())) {
            System.out.println("👉 CHECK DATES");
            throw new DatesEmpruntInvalidesException(
                    "La date de début d'emprunt doit être antérieure à la date de retour prévisionnelle."
            );
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("modeleId reçu = " + dto.getModeleId());

        Utilisateur utilisateur = utilisateurDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));



        Materiel materiel = materielDao.findDisponiblesPourPeriode(
                        dto.getModeleId(),
                        dto.getDateDebutEmprunt(),
                        dto.getDateRetourEmpruntPrevisionelle(),
                        StatutEmprunt.REFUSE
                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new AucunMaterielDisponibleException(
                        "Aucun matériel n'est disponible pour toute la période sélectionnée."
                ));

        Emprunt emprunt = new Emprunt();
        emprunt.setMateriel(materiel);
        emprunt.setDemandeur(utilisateur);
        emprunt.setDateDebutEmprunt(dto.getDateDebutEmprunt());
        emprunt.setDateRetourEmpruntPrevisionelle(dto.getDateRetourEmpruntPrevisionelle());
        emprunt.setStatut(StatutEmprunt.EN_ATTENTE);


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
    public List<EmpruntReponseDto> getAll() {
        return empruntDao.findAll().stream()
                .map(this::versDto)
                .toList();
    }

    public List<EmpruntReponseDto> getMesEmprunts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return empruntDao.findByDemandeurEmailOrderByDateDemandeEmpruntDesc(email)
                .stream()
                .map(this::versDto)
                .toList();
    }

    public void valider(int id) {
        Emprunt emprunt = empruntDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        emprunt.setStatut(StatutEmprunt.APPROUVE);
        empruntDao.save(emprunt);
    }

    public void refuser(int id) {
        Emprunt emprunt = empruntDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        emprunt.setStatut(StatutEmprunt.REFUSE);
        empruntDao.save(emprunt);
    }

    private EmpruntReponseDto versDto(Emprunt emprunt) {
        EmpruntReponseDto dto = new EmpruntReponseDto();

        dto.setId(emprunt.getId());
        dto.setStatut(emprunt.getStatut().name());

        dto.setMaterielId(emprunt.getMateriel().getId());
        dto.setMaterielNom(emprunt.getMateriel().getReference());
        dto.setModeleNom(emprunt.getMateriel().getModele().getNom());

        dto.setDemandeurNom(emprunt.getDemandeur().getNom());
        dto.setDemandeurPrenom(emprunt.getDemandeur().getPrenom());

        dto.setDateDemandeEmprunt(emprunt.getDateDemandeEmprunt());
        dto.setDateDebutEmprunt(emprunt.getDateDebutEmprunt());
        dto.setDateRetourEmpruntPrevisionelle(emprunt.getDateRetourEmpruntPrevisionelle());
        dto.setDateRetourEmpruntReelle(emprunt.getDateRetourEmpruntReelle());

        return dto;
    }
}
