package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.DemandeDTO;
import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.dto.GroupeDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.serivce.ActualiteesService;
import com.springboot.MiniProject.serivce.DemandeService;
import com.springboot.MiniProject.serivce.EtudiantService;
import com.springboot.MiniProject.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issatso/etudiant")
public class EtudiantController {

    @Autowired
    private UserService service;
    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private ActualiteesService actualiteesService;

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

    /////Demande ////
    @GetMapping("/Demande/getAllMesDemande/{email}")
    public ResponseEntity<List<DemandeDTO>> getDemandeByUserEmail(@PathVariable String email ) {
        List<DemandeDTO> demandes = demandeService.getDemandeByUserEmail(email);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }



    @PostMapping("/Demande/AddDemande")
    public ResponseEntity<DemandeDTO> createDemande(@RequestBody DemandeDTO demandeDTO) {
        DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);
        return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
    }
    @PutMapping("/Demande/updateDemande/{id}")
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long id, @RequestBody DemandeDTO demandeDTO) {
        DemandeDTO updatedDemande = demandeService.updateDemande(id, demandeDTO);
        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }


    /////////////////Actualitees //////
    @GetMapping("/Actualitee/{target}")
    public ResponseEntity<List<Actualitees>> getActualiteeEtudiant(@PathVariable String target ) {
        List<Actualitees> news =actualiteesService.getNewsByTargetAudiance(target);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }


/////////////////Matieres ///////////

    @GetMapping("/Matiere/AllMatiere/{id}")
    public List<MatiereDTO> getMatieresDansGroupePourEtudiant(@PathVariable int id ) {
        List<MatiereDTO> mesMat = etudiantService.getMatieresDansGroupePourEtudiant(id);
        return new ResponseEntity<>(mesMat, HttpStatus.OK).getBody();
    }

    @GetMapping("/Groupe/getGroupe/{id}")
    public GroupeDTO getGroupesEtudiant(@PathVariable int id ) {
        return etudiantService.getGroupeEtudiant(id);
    }

}