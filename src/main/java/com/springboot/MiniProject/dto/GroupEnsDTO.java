package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import lombok.Data;

import java.util.List;

@Data
public class GroupEnsDTO {
    private int id;
    private String niveau;
    private String filiere;
    private int numeroGroupe;
    private List<MatiereEnsDTO> matieres;
}
