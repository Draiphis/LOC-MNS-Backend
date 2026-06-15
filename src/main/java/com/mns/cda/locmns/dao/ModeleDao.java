package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.dto.CatalogueModeleDto;
import com.mns.cda.locmns.dto.CatalogueSqlModeleDto;
import com.mns.cda.locmns.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeleDao extends JpaRepository<Modele, Integer> {

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
""", nativeQuery = true)
    long compteStockDisponible(Integer modeleId);

    @Query(value = """
SELECT
    mo.id,
    mo.nom,
    mo.description,
    mo.image,

    COUNT(mat.id) FILTER (
        WHERE NOT EXISTS (
            SELECT 1
            FROM emprunt e
            WHERE e.materiel_id = mat.id
              AND e.date_retour_emprunt_reelle IS NULL
        )
        AND NOT EXISTS (
            SELECT 1
            FROM etat_materiel em
            JOIN etat et ON et.id = em.etat_id
            WHERE em.materiel_id = mat.id
              AND em.date_modification_etat = (
                  SELECT MAX(em2.date_modification_etat)
                  FROM etat_materiel em2
                  WHERE em2.materiel_id = mat.id
              )
              AND et.usure = 'HORS_SERVICE'
        )
    ) AS stockDisponible

FROM modele mo
LEFT JOIN materiel mat ON mat.modele_id = mo.id

JOIN type t ON t.id = mo.type_id
JOIN marque ma ON ma.id = mo.marque_id

WHERE (:type IS NULL OR t.nom = :type)
AND (:marque IS NULL OR ma.nom = :marque)

GROUP BY mo.id, mo.nom, mo.description, mo.image

HAVING (
    :disponible IS NULL
    OR :disponible = FALSE
    OR COUNT(mat.id) FILTER (
        WHERE NOT EXISTS (
            SELECT 1
            FROM emprunt e
            WHERE e.materiel_id = mat.id
              AND e.date_retour_emprunt_reelle IS NULL
        )
        AND NOT EXISTS (
            SELECT 1
            FROM etat_materiel em
            JOIN etat et ON et.id = em.etat_id
            WHERE em.materiel_id = mat.id
              AND em.date_modification_etat = (
                  SELECT MAX(em2.date_modification_etat)
                  FROM etat_materiel em2
                  WHERE em2.materiel_id = mat.id
              )
              AND et.usure = 'HORS_SERVICE'
        )
    ) > 0
)
""", nativeQuery = true)
    List<CatalogueSqlModeleDto> getCatalogue(
            String type,
            String marque,
            Boolean disponible
    );
    }
