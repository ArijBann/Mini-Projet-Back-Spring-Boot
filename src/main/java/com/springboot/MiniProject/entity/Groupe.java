package com.springboot.MiniProject.entity;

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
    private String filière;
    private int numeroGroupe ;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupe", cascade = CascadeType.MERGE)
    private List<Etudiant> etudiantList;

}
