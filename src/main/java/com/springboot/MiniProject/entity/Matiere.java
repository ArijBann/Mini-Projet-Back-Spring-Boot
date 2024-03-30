package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "Matiere")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelleMatiere ;
    private int coefMat ;
    @OneToMany(mappedBy = "matiere")
    private List<EtudMatiere> etudMatieres;
    @OneToMany(mappedBy = "matiere")
    private List<EnseignantMatiere> enseignantMatieres;
    @OneToMany(mappedBy = "matiere")
    private List<SupportCours> supportsCours;
    @ManyToMany
    @JoinTable(name = "Groupe_Matiere",
            joinColumns = @JoinColumn(name = "matiere_id"),
            inverseJoinColumns = @JoinColumn(name = "groupe_id"))
    private List<Groupe> groupes;
}
