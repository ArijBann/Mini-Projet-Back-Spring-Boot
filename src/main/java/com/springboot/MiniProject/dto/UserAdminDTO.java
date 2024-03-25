package com.springboot.MiniProject.dto;

import com.springboot.MiniProject.entity.Admin;
import com.springboot.MiniProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAdminDTO {
    private User user;
    private Admin admin;
}
