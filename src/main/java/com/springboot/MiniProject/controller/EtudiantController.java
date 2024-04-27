package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.SupportCours;
import com.springboot.MiniProject.serivce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
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
    @Autowired
    private EmploiService emploiService;
    @Autowired
    private CompteRenduService compteRenduService;
    @Autowired
    private TravailEtudiantService travailEtudiantService;

    @Autowired
    private SupportCoursService supportCoursService;

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
    @GetMapping("/Actualitee/{targetAudiance}")
    public ResponseEntity<List<Actualitees>> getActualiteeEtudiant(@PathVariable String targetAudiance ) {
        List<Actualitees> news =actualiteesService.getNewsByTargetAudiance(targetAudiance);
        return ResponseEntity.ok(news);
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

    ///////////Emploi////////////

    @GetMapping("/Emploi/{id}")
    public ResponseEntity<?> getEmploiEtudiant(@PathVariable int id ) throws IOException {
        byte[] emploipdf =emploiService.trouverEmploisEtudiantsParGroupeId(id);
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(emploipdf);
    }

    ////////////////// support de cours ///////////////
    @GetMapping("/supportsCours/parGroupe/{idGroupe}")
    public ResponseEntity<List<SupportCoursDTO>> consulterSupportsCoursParGroupe(@PathVariable int idGroupe) throws IOException {

        try {
            // Appelez la méthode pour récupérer les supports de cours par matière
            List<SupportCoursDTO> supportsCours = supportCoursService.trouverSupportsCoursParGroupe(idGroupe);
            return ResponseEntity.ok(supportsCours);
        } catch (IOException e) {
            // Gérez l'exception en renvoyant une réponse HTTP appropriée
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des supports de cours", e);
        }

    }

    @GetMapping("/supportsCoursParMatiere2/{idMatiere}")
    public ResponseEntity<List<SupportCoursDTO>> consulterSupportsCoursParMatiere2(@PathVariable int idMatiere) {
        try {
            // Appelez la méthode pour récupérer les supports de cours par matière
            List<SupportCoursDTO> supportsCours = supportCoursService.consulterSupportsCoursParMatiere2(idMatiere);
            return ResponseEntity.ok(supportsCours);
        } catch (IOException e) {
            // Gérez l'exception en renvoyant une réponse HTTP appropriée
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des supports de cours", e);
        }
    }
    @GetMapping("/supportsCoursParMatiere2Sup/{idSup}/{lien}")
    public ResponseEntity<?> downloadunsup(@PathVariable int idSup,@PathVariable String lien ) throws IOException {
        byte[] supportpdf=supportCoursService.downloadunsup(lien,idSup);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(supportpdf);

    }

///////////////// Compte Rendu ///////////////////////


    @GetMapping("/compteRendu/getByMatiereEtudiant")
    public ResponseEntity<List<CompteRenduDTO>> getComptesRendusByMatiere(@RequestParam int matiereId) {
        List<CompteRenduDTO> comptesRendus = compteRenduService.getComptesRendusByMatiere(matiereId);
        return ResponseEntity.ok(comptesRendus);
    }

    @GetMapping("/compteRendu/getByMatiereEnseignantId")
    public ResponseEntity<List<CompteRenduDTO>> getComptesRendusByMatiereetEns(@RequestParam int matiereId , @RequestParam int enseignantId) {
        List<CompteRenduDTO> comptesRendus = compteRenduService.getComptesRendusByMatiereIdEns(matiereId, enseignantId);
        return ResponseEntity.ok(comptesRendus);
    }
    @GetMapping("/compteRenduByLien/{idcr}/{lien}")
    public ResponseEntity<?> getCompteRenduByLien(@PathVariable int idcr,@PathVariable String lien ) throws IOException {
        byte[] compteRendupdf=compteRenduService.getCompteRenduByLien(lien,idcr);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(compteRendupdf);

    }
    @GetMapping("/compteRenduByFiliereNiveauGroupeMatiere/getByEnsId")
    public ResponseEntity<List<CompteRenduDTO>> getCompteRenduByFiliereNiveauGroupeMatiere(@RequestParam int enseignantId ,@RequestParam int idMatiere,@RequestParam int idGroupe) {
        List<CompteRenduDTO> comptesRendus = compteRenduService.getCompteRenduByFiliereNiveauGroupeMatiere(enseignantId ,idMatiere,idGroupe);
        return ResponseEntity.ok(comptesRendus);
    }
    @GetMapping("/travailEtudiant/getByCompteRendu")
    public ResponseEntity<List<TravailEtudiantDTO>> getTravauxEtudiantByCompteRendu(@RequestParam int compteRenduId) {
        List<TravailEtudiantDTO> travauxEtudiant = travailEtudiantService.getTravauxEtudiantByCompteRendu(compteRenduId);
        return ResponseEntity.ok(travauxEtudiant);
    }
    @GetMapping("/travailEtudiantByLien/{idcr}/{lien}")
    public ResponseEntity<?> getTravailEtudiantByLien(@PathVariable int idcr,@PathVariable String lien ) throws IOException {
        byte[] travailEtudiantpdf=travailEtudiantService.getTravailEtudiantByLien(lien,idcr);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(travailEtudiantpdf);

    }
    @PostMapping("/travailEtudiant/add")
    public ResponseEntity<String> ajoutertravailEtudiant(@RequestParam int idCompteRendu,
                                                     @RequestParam MultipartFile fichier,
                                                         @RequestParam String message,
                                                         @RequestParam int etudiantId) throws ParseException, IOException {
        travailEtudiantService.addTravailEtudiant(idCompteRendu, fichier, message, etudiantId);
        return ResponseEntity.ok("travailEtudiant  créé avec succès !");
    }


}