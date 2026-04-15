package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpruntDao extends JpaRepository<Emprunt, Integer> {
}
