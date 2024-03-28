package com.springboot.MiniProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnseignantDTO {
    private int idEnseignant;
    private double num_prof;
    private List<Integer> idGroupes ;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Double CIN;
    private Date date_nais;
    private double numtel;
}
