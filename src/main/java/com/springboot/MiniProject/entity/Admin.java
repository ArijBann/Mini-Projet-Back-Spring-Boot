package com.springboot.MiniProject.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int num_bureau;
    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private User user;
}
