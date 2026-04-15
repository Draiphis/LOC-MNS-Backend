package com.mns.cda.locmns.dao;


import com.mns.cda.locmns.model.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentationDao extends JpaRepository<Documentation, Integer> {
}
