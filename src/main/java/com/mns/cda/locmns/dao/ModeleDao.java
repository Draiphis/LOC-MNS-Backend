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

    @Query("""

            SELECT COUNT(m)
FROM Materiel m
WHERE m.modele.id = :modeleId
AND NOT EXISTS (
    SELECT em
    FROM EtatMateriel em
    WHERE em.materiel = m
    AND em.dateModificationEtat = (
        SELECT MAX(em2.dateModificationEtat)
        FROM EtatMateriel em2
        WHERE em2.materiel = m
    )
    AND em.etat.usure = com.mns.cda.locmns.model.EtatUsure.HORS_SERVICE
)
""")
    int calculerNonHsStockDisponible(Integer modeleId);

    @Query(value = """
WITH dernier_etat AS (
    SELECT DISTINCT ON (em.materiel_id)
        em.materiel_id,
        e.usure
    FROM etat_materiel em
    JOIN etat e ON e.id = em.etat_id
    ORDER BY em.materiel_id, em.date_modification_etat DESC
)
SELECT
    mo.id,
    mo.nom,
    mo.description,
    mo.image,

    COUNT(mat.id) FILTER (
        WHERE de.usure <> 'HORS_SERVICE'
    ) AS stockDisponible

FROM modele mo

LEFT JOIN materiel mat ON mat.modele_id = mo.id
LEFT JOIN dernier_etat de ON de.materiel_id = mat.id

JOIN type t ON t.id = mo.type_id
JOIN marque ma ON ma.id = mo.marque_id

WHERE (:type IS NULL OR t.nom = :type)
AND (:marque IS NULL OR ma.nom = :marque)

GROUP BY mo.id, mo.nom, mo.description, mo.image

HAVING (
    :disponible IS NULL
    OR :disponible = FALSE
    OR COUNT(mat.id) FILTER (
        WHERE de.usure <> 'HORS_SERVICE'
    ) > 0
)
""", nativeQuery = true)
    List<CatalogueSqlModeleDto> getCatalogue(
            String type,
            String marque,
            Boolean disponible
    );
    }
