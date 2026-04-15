package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatDao extends JpaRepository<Etat, Integer> {
}
