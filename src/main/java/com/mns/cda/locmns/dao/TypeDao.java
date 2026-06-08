package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.dto.StockParTypeDto;
import com.mns.cda.locmns.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {

    @Query(value = """
SELECT
    t.id AS id,
    t.nom AS nom,
    COUNT(mo.id) AS nbModeleEnStock
FROM type t
LEFT JOIN modele mo ON mo.type_id = t.id
GROUP BY t.id, t.nom
ORDER BY t.nom
""", nativeQuery = true)
    List<StockParTypeDto> getNbModelesEnStockParType();
}
