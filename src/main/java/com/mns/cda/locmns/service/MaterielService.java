package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.MaterielDao;
import com.mns.cda.locmns.dao.EmpruntDao;
import com.mns.cda.locmns.dto.CreateMaterielDto;
import com.mns.cda.locmns.dto.DisponibiliteModeleDto;
import com.mns.cda.locmns.dto.MaterielDisponibleDto;
import com.mns.cda.locmns.dto.UpdateMaterielDto;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.model.StatutEmprunt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MaterielService {


    private final EtatMaterielService etatMaterielService;

    private final MaterielDao materielDao;
    private final EmpruntDao empruntDao;

    // CREATE
    public Materiel create(CreateMaterielDto dto) {
        Materiel u = new Materiel();
        
        u.setReference(dto.getReference());
        

        return materielDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateMaterielDto dto) {
        Materiel u = materielDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Materiel non trouvé"));
        
        u.setReference(dto.getReference());
        materielDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!materielDao.existsById(id)) {
            throw new RuntimeException("Materiel non trouvé");
        }
        materielDao.deleteById(id);
    }

    // GET ONE
    public Materiel getById(int id) {
        return materielDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Materiel non trouvé"));
    }

    // GET ALL
    public java.util.List<Materiel> getAll() {
        return materielDao.findAll();
    }

    public boolean estDisponible(Integer modeleId) {
        return materielDao.auMoinsUnMaterielDisponible(modeleId);

    }

    public long getStockDisponible(Integer modeleId) {
        return materielDao.stockDisponibleParModeleId(modeleId);
    }

    public List<MaterielDisponibleDto> getDisponibles(Integer modeleId) {

        return materielDao.findDisponiblesByModeleId(modeleId)
                .stream()
                .map(m -> {
                    MaterielDisponibleDto dto = new MaterielDisponibleDto();

                    dto.setId(m.getId());
                    dto.setReference(m.getReference());

                    dto.setModeleId(m.getModele().getId());
                    dto.setModeleNom(m.getModele().getNom());

                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public DisponibiliteModeleDto getDisponibiliteModele(Integer modeleId) {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate finCalendrier = aujourdHui.plusYears(1);
        long nombreExemplaires = materielDao.countByModeleId(modeleId);

        if (nombreExemplaires == 0) {
            return new DisponibiliteModeleDto(
                    false,
                    null,
                    0,
                    "Aucun exemplaire de ce modèle n'est disponible à la réservation.",
                    toutesLesDates(aujourdHui, finCalendrier)
            );
        }

        List<LocalDate> datesIndisponibles = calculerDatesIndisponibles(
                modeleId,
                aujourdHui,
                finCalendrier,
                nombreExemplaires
        );
        boolean disponibleAujourdhui = !datesIndisponibles.contains(aujourdHui);

        if (disponibleAujourdhui) {
            return new DisponibiliteModeleDto(
                    true,
                    aujourdHui,
                    nombreExemplaires,
                    "Les jours grisés correspondent aux périodes où tous les exemplaires sont réservés.",
                    datesIndisponibles
            );
        }

        LocalDate premiereDateDisponible = aujourdHui;
        while (!premiereDateDisponible.isAfter(finCalendrier)
                && datesIndisponibles.contains(premiereDateDisponible)) {
            premiereDateDisponible = premiereDateDisponible.plusDays(1);
        }

        if (premiereDateDisponible.isAfter(finCalendrier)) {
            return new DisponibiliteModeleDto(
                    false,
                    null,
                    nombreExemplaires,
                    "Aucun créneau n'est disponible sur les douze prochains mois.",
                    datesIndisponibles
            );
        }

        return new DisponibiliteModeleDto(
                true,
                premiereDateDisponible,
                nombreExemplaires,
                "Tous les exemplaires sont réservés jusqu'au "
                        + premiereDateDisponible.minusDays(1) + ".",
                datesIndisponibles
        );
    }

    private List<LocalDate> calculerDatesIndisponibles(
            Integer modeleId,
            LocalDate dateDebut,
            LocalDate dateFin,
            long nombreExemplaires
    ) {
        List<Emprunt> reservations = empruntDao.findReservationsSurPeriode(
                modeleId,
                dateDebut,
                dateFin,
                StatutEmprunt.REFUSE
        );
        Map<LocalDate, Set<Integer>> materielsOccupesParDate = new HashMap<>();

        for (Emprunt reservation : reservations) {
            LocalDate debut = reservation.getDateDebutEmprunt().isBefore(dateDebut)
                    ? dateDebut
                    : reservation.getDateDebutEmprunt();
            LocalDate retour = reservation.getDateRetourEmpruntReelle() != null
                    ? reservation.getDateRetourEmpruntReelle()
                    : reservation.getDateRetourEmpruntPrevisionelle();
            LocalDate fin = retour.isAfter(dateFin) ? dateFin : retour;

            for (LocalDate date = debut; !date.isAfter(fin); date = date.plusDays(1)) {
                materielsOccupesParDate
                        .computeIfAbsent(date, ignored -> new HashSet<>())
                        .add(reservation.getMateriel().getId());
            }
        }

        List<LocalDate> datesIndisponibles = new ArrayList<>();
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            if (materielsOccupesParDate.getOrDefault(date, Set.of()).size() >= nombreExemplaires) {
                datesIndisponibles.add(date);
            }
        }
        return datesIndisponibles;
    }

    private List<LocalDate> toutesLesDates(LocalDate dateDebut, LocalDate dateFin) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }

}
