package com.springboot.MiniProject.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "Enseignant_Matiere")
public class EnseignantMatiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "id_ens")
    private Enseignant enseignant;

    private int absence;

}
