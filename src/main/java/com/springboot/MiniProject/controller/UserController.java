package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.AuthRequest;
import com.springboot.MiniProject.dto.UserAdminDTO;
import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.dto.UserEtudiantDTO;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.serivce.JwtService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issatso")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //cette PAGE est accessible par tout le monde
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/user/enseignant")
    public String addNewEns(@RequestBody UserEnseigantDTO userEnseigantDTO){
        return service.addEns(userEnseigantDTO);
    }

    @PostMapping("/user/etudiant")
    public String addNewEns(@RequestBody UserEtudiantDTO userEtudiantDTO){
        return service.addEtud(userEtudiantDTO);
    }
    @PostMapping("/user/admin")
    public String addNewEns(@RequestBody UserAdminDTO userAdminDTO){
        return service.addAdmin(userAdminDTO);
    }
    //cette PAGE est accessible par les etudiants seulement
    @GetMapping("/welcome/etud")
    @PreAuthorize("hasAuthority('ROLE_ETUD')")
    public String welcomeEtud() {
        return "Welcome etud";
    }

    //cette PAGE est accessible par les admins seulement
    @GetMapping("/welcome/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeAdmin() {
        return "Welcome ens";
    }
    //cette PAGE est accessible par les enseignants seulement
    @GetMapping("/welcome/ens")
    @PreAuthorize("hasAuthority('ROLE_ENS')")
    public String welcomeEns() {
        return "Welcome ens";
    }


    @PostMapping("/authentificat")
    public String authentificateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getNom(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getNom());
        }else{
            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}
