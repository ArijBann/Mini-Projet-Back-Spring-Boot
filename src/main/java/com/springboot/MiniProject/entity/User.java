package com.springboot.MiniProject.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "Myuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //global info
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Double CIN;
    private Date date_nais;
    private double numtel;
    private String roles;
    //etudiant
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_etud_id")
    private Etudiant etudiant;
    //enseignant
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_ens_id")
    private Enseignant enseignant;
    //admin
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_admin_id")
    private Admin admin;
}
