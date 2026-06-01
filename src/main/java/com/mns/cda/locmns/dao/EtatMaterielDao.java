package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.EtatMateriel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatMaterielDao extends JpaRepository<EtatMateriel, EtatMateriel.Key> {
}
