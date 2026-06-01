package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeleDao extends JpaRepository<Modele, Integer> {

    @Query("SELECT m FROM Modele m JOIN FETCH m.materiaux")
    List<Modele> findAllWithMateriels();
}
