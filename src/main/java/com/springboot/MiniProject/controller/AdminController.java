package com.springboot.MiniProject.controller;


import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.serivce.EnseignantService;
import com.springboot.MiniProject.serivce.EtudiantService;
import com.springboot.MiniProject.serivce.JwtService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issatso/admin")
public class AdminController {

    @Autowired
    private UserService service;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EtudiantService etudiantService;

    //cette PAGE est accessible par les admins seulement
    @GetMapping("/welcome/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeAdmin() {
        return "Welcome ens";
    }
    @PostMapping("/add/enseignant")
    public String addNewEns(@RequestBody UserEnseigantDTO userEnseigantDTO){
        return service.addEns(userEnseigantDTO);
    }

    @PostMapping("/add/etudiant")
    public String addNewEtud(@RequestBody UserEtudiantDTO userEtudiantDTO){
        return service.addEtud(userEtudiantDTO);
    }
    @PostMapping("/add/admin")
    public String addNewAdm(@RequestBody UserAdminDTO userAdminDTO){
        return service.addAdmin(userAdminDTO);
    }

    @DeleteMapping("/delete/enseignant/{id}")
    public String deleteEnsegnant(@PathVariable int id){
        return service.deleteEns(id);
    }
    @DeleteMapping("/delete/etudiant/{id}")
    public String deleteEtudiant(@PathVariable int id){
        return service.deleteEtud(id);
    }
    @DeleteMapping("/delete/admin/{id}")
    public String deleteAdmin(@PathVariable int id){
        return service.deleteAdmin(id);
    }
    @PutMapping("/update/enseignant")
    public EnseignantDTO updateEnseignant(@RequestBody EnseignantDTO Enseigant){return service.updateEnseignant(Enseigant);}

    @PutMapping("/update/etudiant")
    public EtudiantDTO updateEtudiant(@RequestBody EtudiantDTO etudiant){return service.updateEtudiant(etudiant);}
 /*   @PutMapping("/update/admin")
    public UserAdminDTO updateAdmin(@RequestBody UserAdminDTO userAdminDTO){return service.updateAdmin(userAdminDTO);}
*/

    @GetMapping("/allEnseignants")
    public List<EnseignantDTO> getAllEnseignants() {
        return service.findAllEnseignant();
    }

    /*@GetMapping("/Enseignantbyemail/{email}")
    public ResponseEntity<UserEnseigantDTO> getEnseignantByEmail(@PathVariable String email) {
        UserEnseigantDTO enseignantDTO = service.getEnseignantByEmail(email);
        if (enseignantDTO != null) {
            return ResponseEntity.ok(enseignantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    @GetMapping("/numProf/{numProf}")
    public ResponseEntity<EnseignantDTO> getEnseignantByNumProf(@PathVariable int numProf) {
        EnseignantDTO enseignantDTO = service.findByNumProf(numProf);
        if (enseignantDTO != null) {
            return ResponseEntity.ok(enseignantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getEtudiantByInscrit/{numInscri}")
    public ResponseEntity<EtudiantDTO> getEtudiantByInscri(@PathVariable double numInscri) {
        EtudiantDTO userEtudiantDTO = service.findByNumInscri(numInscri);
        if (userEtudiantDTO != null) {
            return ResponseEntity.ok(userEtudiantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allEtudiant")
    public List<EtudiantDTO> getAllEtudiants() {
        return service.findAllEtudiant();
    }
    @GetMapping("/getEtudiantByGroupe/{idGroupe}")
    public List<EtudiantDTO> getEtudiantsByGroupe(@PathVariable int idGroupe) {
        return service.findByIdGroupe(idGroupe);
    }
}
