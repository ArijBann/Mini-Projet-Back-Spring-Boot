package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatiereRepository  extends JpaRepository<Matiere, Integer> {
    Optional<Matiere> findByLibelleMatiere(String libMat);
    @Query("SELECT DISTINCT m FROM Matiere m INNER JOIN m.groupes g WHERE g.filiere.id = :idFiliere AND g.niveau = :niveau")
    List<Matiere> findByFiliereIdAndNiveau(int idFiliere, String niveau);
   // Optional <List<Matiere> >findByEnseignantId(int idEnseignant);
   // Optional <List <Matiere>> findByGroupe(int idGroupe);

}
