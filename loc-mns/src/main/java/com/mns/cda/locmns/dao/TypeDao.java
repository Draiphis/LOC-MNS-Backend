package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {
}
