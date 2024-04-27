package com.springboot.MiniProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "Demande")

public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sujet;

    private String description;

    private Date dateCreation;

    private String statut;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
}
