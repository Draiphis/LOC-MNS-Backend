package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.model.RoleNom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleNom role);
}
