package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Demande;
import com.springboot.MiniProject.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande,Long> {
    //Optional<Demande> findByUserEmail(String email);
}
