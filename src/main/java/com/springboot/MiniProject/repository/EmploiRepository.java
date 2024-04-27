package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmploiRepository extends JpaRepository<Emploi,Integer> {

    List<Emploi> findByFiliere_Id(int idFiliere);
    List<Emploi> findByFiliere_Nom(String NomFiliere);
    Emploi findByGroupe_Id(int  idGroupe);
   Emploi findByEnseignant_Id(int  idEns);


}
