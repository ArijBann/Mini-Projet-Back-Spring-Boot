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
    private int idEtudMatieres;
    private int idEnseignantMatieres;
    private int idSupportsCours;
    private List <Integer> idGroupes;
}
