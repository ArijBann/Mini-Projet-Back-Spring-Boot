package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.EnseignantMatiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantMatiereRepository  extends JpaRepository<EnseignantMatiere, Integer> {
}
