package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.serivce.EtudiantService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issatso/etudiant")
public class EtudiantController {

    @Autowired
    private UserService service;
    @Autowired
    private EtudiantService etudiantService;

    //cette PAGE est accessible par les etudiants seulement
    @GetMapping("/welcome/etud")
    @PreAuthorize("hasAuthority('ROLE_ETUD')")
    public String welcomeEtud() {
        return "Welcome etud";
    }

    @PutMapping("/update/NumTel")
    public EtudiantDTO updateErudiantInfo(@RequestBody EtudiantDTO etudiantDTO){
        return etudiantService.updateEtudiantInfo(etudiantDTO);
    }

}
