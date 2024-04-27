package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "travail_etudiant")
@Getter
@Setter
@Data
public class TravailEtudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name = "fichier_pdf")
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] fichier;
    private String message;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "compte_rendu_id")
    private CompteRendu compteRendu;
}
