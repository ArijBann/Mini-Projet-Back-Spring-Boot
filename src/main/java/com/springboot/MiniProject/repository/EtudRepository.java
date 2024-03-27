package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtudRepository extends JpaRepository<Etudiant,Integer> {
    Optional<Etudiant> findEtudiantByNum_inscri(int numInscrit);
}
