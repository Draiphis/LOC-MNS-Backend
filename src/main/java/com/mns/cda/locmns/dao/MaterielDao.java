package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterielDao extends JpaRepository<Materiel, Integer> {
}
