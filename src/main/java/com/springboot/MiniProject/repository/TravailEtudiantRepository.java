package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.CompteRendu;
import com.springboot.MiniProject.entity.TravailEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravailEtudiantRepository extends JpaRepository<TravailEtudiant, Integer> {
    List<TravailEtudiant> findByCompteRendu_Id(int id);
}