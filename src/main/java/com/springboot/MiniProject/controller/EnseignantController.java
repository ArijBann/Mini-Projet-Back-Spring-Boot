package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.serivce.EnseignantService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issatso/enseignant")
public class EnseignantController {

    @Autowired
    private UserService service;
    @Autowired
    private EnseignantService enseignantService;


    //cette PAGE est accessible par les enseignants seulement
    @GetMapping("/welcome/ens")
    @PreAuthorize("hasAuthority('ROLE_ENS')")
    public String welcomeEns() {
        return "Welcome ens";
    }
    @PutMapping("/update/NumTel")
    public EnseignantDTO updateEnseignantInfo(@RequestBody EnseignantDTO enseignantDTO){
        return enseignantService.updateEtudiantInfo(enseignantDTO);
    }

}
