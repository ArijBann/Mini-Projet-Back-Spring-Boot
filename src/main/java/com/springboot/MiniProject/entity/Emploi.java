package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "emploi")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Emploi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(name = "pdf_contenu")
    private byte[] pdfContenu;

    //@Transient // Ne sera pas persisté en base de données
    //private MultipartFile pdfFile;
    @Temporal(TemporalType.DATE)
    private Date date;
    private boolean estEnseignant;
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

}