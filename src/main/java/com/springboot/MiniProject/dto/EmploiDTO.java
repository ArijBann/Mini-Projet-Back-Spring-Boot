package com.springboot.MiniProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploiDTO {
    private int id;
    private Date date;
    private boolean estEnseignant;
    private String lienPDF;
    private int  idEnseignant;
    private int idFiliere;
    private int idGroupe;
}
