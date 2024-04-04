package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere,Long> {
    Filiere findByNom(String nom);
}
