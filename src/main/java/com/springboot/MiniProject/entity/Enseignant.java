package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Data
@Entity
@Table(name = "enseignant")
public class Enseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numProf;
    private String diplome ;
    @OneToOne(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private User user;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable( name = "ens_groupes",
            joinColumns = @JoinColumn(name = "ens_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "groupe_id", referencedColumnName = "id"))
    private List<Groupe> roles = new ArrayList<>();
}

