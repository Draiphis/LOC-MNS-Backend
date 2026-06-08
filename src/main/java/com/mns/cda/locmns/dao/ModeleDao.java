package com.mns.cda.locmns.dao;


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
}
