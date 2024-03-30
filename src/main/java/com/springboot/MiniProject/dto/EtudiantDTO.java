package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDTO {
    private int idEtudiant;
    private double num_inscri;
    private int IdGroupe;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Double CIN;
    private Date date_nais;
    private double numtel;

}
