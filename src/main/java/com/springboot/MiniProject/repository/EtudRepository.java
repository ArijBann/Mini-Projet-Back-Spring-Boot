package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudRepository extends JpaRepository<Etudiant,Integer> {
    Etudiant findEtudiantByNumInscri(double numInscrit);

    List <Etudiant> findByGroupeId(int idGroupe);
}
