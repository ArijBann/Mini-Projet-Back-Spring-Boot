package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import lombok.Data;

@Data
public class UserEtudiantDTO {
    private User user;
    private Etudiant etudiant;
}
