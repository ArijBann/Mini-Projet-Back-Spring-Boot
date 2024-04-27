package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "compte_rendu")
@Getter
@Setter
@Data
public class CompteRendu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private String description;
    @Lob
    @Column(name = "fichier_pdf")
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] fichier;
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @OneToMany(mappedBy = "compteRendu")
    private List<TravailEtudiant> travauxEtudiants;
}