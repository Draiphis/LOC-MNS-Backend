package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}

