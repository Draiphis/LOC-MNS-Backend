package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.model.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterielDao extends JpaRepository<Materiel, Integer> {

    @Query("""
        SELECT COUNT(m) > 0
        FROM Materiel m
        WHERE m.modele.id = :modeleId
        AND NOT EXISTS (
            SELECT 1
            FROM Emprunt e
            WHERE e.materiel = m
            AND e.dateRetourEmpruntReelle IS NULL
        )
    """)
    boolean auMoinsUnMaterielDisponible(int modeleId);

    @Query(value = """
        SELECT COUNT(m.id)
        FROM materiel m
        WHERE m.modele_id = :modeleId

        AND NOT EXISTS (
            SELECT 1
            FROM emprunt e
            WHERE e.materiel_id = m.id
              AND e.date_retour_emprunt_reelle IS NULL
        )

        AND NOT EXISTS (
            SELECT 1
            FROM etat_materiel em
            JOIN etat et ON et.id = em.etat_id
            WHERE em.materiel_id = m.id
              AND et.usure = 'HORS_SERVICE'
        )
    """, nativeQuery = true)
    long stockDisponibleParModeleId(int modeleId);

    @Query("""
SELECT m
FROM Materiel m
WHERE m.modele.id = :modeleId
AND NOT EXISTS (
    SELECT 1
    FROM Emprunt e
    WHERE e.materiel.id = m.id
    AND e.dateRetourEmpruntReelle IS NULL
    AND e.dateDebutEmprunt <= CURRENT_DATE
)
""")
    List<Materiel> findDisponiblesByModeleId(Integer modeleId);

    long countByModeleId(Integer modeleId);

    @Query("""
        SELECT m
        FROM Materiel m
        WHERE m.modele.id = :modeleId
        AND NOT EXISTS (
            SELECT 1
            FROM Emprunt e
            WHERE e.materiel = m
            AND e.statut <> :statutRefuse
            AND e.dateDebutEmprunt <= :dateFin
            AND COALESCE(e.dateRetourEmpruntReelle, e.dateRetourEmpruntPrevisionelle) >= :dateDebut
        )
        ORDER BY m.id
    """)
    List<Materiel> findDisponiblesPourPeriode(
            Integer modeleId,
            LocalDate dateDebut,
            LocalDate dateFin,
            StatutEmprunt statutRefuse
    );
}

