package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "Etudiant_Matiere")
public class EtudMatiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_etud")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    private double noteEx;
    private double noteDs;
    private double noteTp;
    private int nbrAbscence;

}
