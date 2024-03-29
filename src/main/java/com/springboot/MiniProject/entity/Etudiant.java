package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity
@Table(name = "etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "num_inscri")
    private double numInscri;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;



}
