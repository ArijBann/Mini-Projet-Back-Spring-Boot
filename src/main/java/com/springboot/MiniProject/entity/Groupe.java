package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "groupe")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int niveau;
    private String filière;
    private int numeroGroupe ;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupe", cascade = CascadeType.ALL)
    private List<UserInfo> blogList;

}
