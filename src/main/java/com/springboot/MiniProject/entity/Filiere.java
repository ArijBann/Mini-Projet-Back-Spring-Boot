package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "filiere")
@Getter @Setter @Data
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

}

