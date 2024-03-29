package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.EtudMatiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudMatiereRepository  extends JpaRepository<EtudMatiere, Integer> {
}
