package com.springboot.MiniProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeDTO {
        private Long id;
        private String sujet;
        private String description;
        private Date dateCreation;
        private String statut;
        private String userEmail;
    }


