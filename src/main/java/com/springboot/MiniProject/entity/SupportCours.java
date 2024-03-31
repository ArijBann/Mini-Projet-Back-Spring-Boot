package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "Support_Cours")
public class SupportCours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelleSupport;
    @Lob
    private byte[] fichier;
    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

}
