package com.springboot.MiniProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String prenom;
    private String email;
    private Double CIN;
    private Date date_nais;
    private double numtel;
    private String role;
    private String token;
}
