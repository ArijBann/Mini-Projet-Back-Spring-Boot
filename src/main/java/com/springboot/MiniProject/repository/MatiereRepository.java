package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatiereRepository  extends JpaRepository<Matiere, Integer> {
    Optional<Matiere> findByLibelleMatiere(String libMat);
   // Optional <List<Matiere> >findByEnseignantId(int idEnseignant);
   // Optional <List <Matiere>> findByGroupe(int idGroupe);

}
