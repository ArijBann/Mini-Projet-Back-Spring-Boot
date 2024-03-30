package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.EnseignantMatiere;
import com.springboot.MiniProject.entity.EtudMatiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnseignantMatiereRepository  extends JpaRepository<EnseignantMatiere, Integer> {
    Optional<List<EnseignantMatiere>> findByEnseignantId(int idEtud);
    Optional<List<EnseignantMatiere>> findBymatiereId(int idMat);
}
