package com.springboot.MiniProject.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravailEtudiantDTO {
    private int id;
    private String lienPdf;
    private String message;
    private String etudiantNom;
}