package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/user/enseignant")
    public String addNewEns(@RequestBody User user,@RequestBody Enseignant enseignant){
        return service.addEns(user,enseignant);
    }

    @GetMapping("/welcome/etud")
    @PreAuthorize("hasAuthority('ROLE_ETUD')")
    public String welcomeEtud() {
        return "Welcome etud";
    }

    @GetMapping("/welcome/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeAdmin() {
        return "Welcome ens";
    }

    @GetMapping("/welcome/ens")
    @PreAuthorize("hasAuthority('ROLE_ENS')")
    public String welcomeEns() {
        return "Welcome ens";
    }
}
