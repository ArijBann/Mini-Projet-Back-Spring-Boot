package com.springboot.MiniProject.repository;
import com.springboot.MiniProject.entity.CompteRendu;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRenduRepository extends JpaRepository<CompteRendu, Integer> {
    List<CompteRendu> findByMatiere_Id(int matiereId);
    List<CompteRendu> findByEnseignant_Id(int enseignantId);
    List<CompteRendu> findByGroupe_Id(int groupeId);
  /*  List<CompteRendu> findByEnseignant(Enseignant enseignant);
    List<CompteRendu> findByMatiere(Matiere matiere);
    List<CompteRendu> findByEnseignant_IdAndMatiere_IdAndMatiere_Groupes_IdAndMatiere_Groupes_Niveau(int enseignantId, int matiereId, int groupeId, String niveau);
    List<CompteRendu>findByMatiere_Filiere_NomAndMatiere_Groupes_Id (String filiere, int groupeId);
    */
}
