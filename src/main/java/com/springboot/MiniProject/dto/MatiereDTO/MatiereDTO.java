package com.springboot.MiniProject.dto.MatiereDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatiereDTO {
    private int id;
    private String libelleMatiere ;
    private int coefMat ;
    private List <Long> idEtudMatieres;
    private List <Long> idEnseignantMatieres;
    private List <Integer> idSupportsCours;
    private List <Integer> idGroupes;
}
