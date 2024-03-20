package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {
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
    //admin info
    private int num_bureau;
    //prof info
    private int numProf;
    private String diplome ;
    //etud info
    private double num_inscri;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    private String roles;
}
