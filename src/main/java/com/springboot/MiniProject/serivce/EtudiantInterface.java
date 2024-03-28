package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EtudiantDTO;

import java.util.List;

public interface EtudiantInterface {
    EtudiantDTO findByNumInscri(double numInscri);

   List <EtudiantDTO> findByIdGroupe(int idGroupe);
   List <EtudiantDTO> findAllEtudiant();
}
