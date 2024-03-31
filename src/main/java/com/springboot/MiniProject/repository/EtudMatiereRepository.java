package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.EtudMatiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EtudMatiereRepository  extends JpaRepository<EtudMatiere, Integer> {
    Optional<List<EtudMatiere>> findByEtudiantId(int idEtud);
    Optional<List<EtudMatiere>> findBymatiereId(int idMat);

}
