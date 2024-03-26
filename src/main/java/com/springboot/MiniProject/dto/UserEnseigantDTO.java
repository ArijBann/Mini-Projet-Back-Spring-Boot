package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEnseigantDTO {
    private User user;
    private Enseignant enseignant;


}
