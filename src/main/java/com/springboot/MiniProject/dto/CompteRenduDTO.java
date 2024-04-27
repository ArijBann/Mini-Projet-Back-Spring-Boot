package com.springboot.MiniProject.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteRenduDTO {
    private int id;
    private String titre;
    private String description;
    private String lienPDF;
    private Date deadline;
    private String matiereNom;
    private String groupeNom;
    private String enseignantNom;
}

