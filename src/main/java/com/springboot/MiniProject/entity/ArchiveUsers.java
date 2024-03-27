package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "archiveUsers")
public class ArchiveUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date_suppression;
    private String description ;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Double CIN;
    private Date date_nais;
    private double numtel;
    private String roles;
    //etudiant
    @ManyToOne
    @JoinColumn(name = "fk_etud_id")
    private Etudiant etudiant;
    //enseignant
    @ManyToOne
    @JoinColumn(name = "fk_ens_id")
    private Enseignant enseignant;
    //admin
    @ManyToOne
    @JoinColumn(name = "fk_admin_id")
    private Admin admin;
}
