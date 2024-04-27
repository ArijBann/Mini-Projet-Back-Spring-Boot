package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Data
@Entity
@Table(name = "enseignant")
public class Enseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numProf;
    private String diplome ;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable( name = "ens_groupes",
            joinColumns = @JoinColumn(name = "ens_id"),
            inverseJoinColumns = @JoinColumn(name = "groupe_id"))
    private List<Groupe> groupes ;

    @OneToMany(mappedBy = "enseignant")
    private List<EnseignantMatiere> enseignantMatieres;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;


}

