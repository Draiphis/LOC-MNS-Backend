package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.roles WHERE u.email = :email ")
    Optional<Utilisateur> findByEmailWithRoles (@Param("email") String email);

    String email(String email);
}
