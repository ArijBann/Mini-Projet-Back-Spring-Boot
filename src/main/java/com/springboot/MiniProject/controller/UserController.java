package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.config.UserInfoDetails;
import com.springboot.MiniProject.config.UserInfoDetailsService;
import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.entity.RefreshToken;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.serivce.JwtService;
import com.springboot.MiniProject.serivce.UserService;
import com.springboot.MiniProject.serivce.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issatso")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserInfoDetailsService userInfoDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
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

    @Transactional
    @PostMapping("/logout")
    public ResponseEntity<?> logout (@RequestHeader("Authorization") String refreshToken){
        int userId = refreshTokenService.getLoggedInUser(refreshToken).get().getUser().getId();
        refreshTokenService.deleteRefreshToken(userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authentificat")
    public JwtResponse authentificateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getEmail()))
                    .token(refreshToken.getToken()).build();
        }else{
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getEmail());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

}