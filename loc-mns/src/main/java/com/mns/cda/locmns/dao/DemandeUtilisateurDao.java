package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.DemandeUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeUtilisateurDao extends JpaRepository<DemandeUtilisateur, Integer> {
}
