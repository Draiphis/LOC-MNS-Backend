package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.model.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpruntDao extends JpaRepository<Emprunt, Integer> {

    @Query("""
        SELECT e
        FROM Emprunt e
        WHERE e.materiel.modele.id = :modeleId
        AND e.statut <> :statutRefuse
        AND e.dateDebutEmprunt <= :dateFin
        AND COALESCE(e.dateRetourEmpruntReelle, e.dateRetourEmpruntPrevisionelle) >= :dateDebut
    """)
    List<Emprunt> findReservationsSurPeriode(
            Integer modeleId,
            LocalDate dateDebut,
            LocalDate dateFin,
            StatutEmprunt statutRefuse
    );
}
