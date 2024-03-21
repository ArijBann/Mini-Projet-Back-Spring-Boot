package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Integer> {
}
