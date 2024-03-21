package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import lombok.Data;

@Data

public class UserEnseigantDTO {
    private User user;
    private Enseignant enseignant;
}
