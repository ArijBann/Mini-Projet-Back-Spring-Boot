package com.springboot.MiniProject.entity;

import com.springboot.MiniProject.dto.EnseignantDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@Table(name = "groupe")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String niveau;
    private int numeroGroupe ;
    @ManyToMany(mappedBy = "groupes")
    private List <Enseignant> enseignants ;
   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupe", cascade = CascadeType.MERGE)
    private List<Etudiant> etudiantList;*/
   @ManyToMany(mappedBy = "groupes")
   private List<Matiere> matieres;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

}
