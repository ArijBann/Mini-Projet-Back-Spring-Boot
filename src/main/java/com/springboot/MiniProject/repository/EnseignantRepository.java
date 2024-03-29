package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Integer> {
   // Enseignant findByEmail(String email);
   Enseignant findByNumProf(double numProf);
}
