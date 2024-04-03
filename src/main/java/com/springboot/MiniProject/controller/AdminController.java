package com.springboot.MiniProject.controller;



import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.serivce.*;
import com.springboot.MiniProject.serivce.Matiere_Service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/issatso/admin")
public class AdminController {

    @Autowired
    private UserService service;
    @Autowired
    private MatiereService matiereService;
    @Autowired
    private ActualiteesService actualiteesService;
    @Autowired
    private DemandeService demandeService;

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

    @DeleteMapping("/delete/enseignant/{numProf}/{desc}")
    public String deleteEnsegnant(@PathVariable int numProf,@PathVariable String desc){
        return service.deleteEns(numProf,desc);
    }

    @DeleteMapping("/delete/etudiant/{numinscri}/{desc}")
    public String deleteEtudiant(@PathVariable int numinscri,@PathVariable String desc){
        return service.deleteEtud(numinscri,desc);
    }
    @GetMapping("/Actualitees/allActualitees")
    public List<Actualitees> getAllNews() {
        return actualiteesService.getAllNews();
    }

    @GetMapping("/Actualitees/{id}")
    public Actualitees getNewsById(@PathVariable Long id) {
        return actualiteesService.getNewsById(id);
    }

    @PostMapping("/Actualitees/AddActualitees")
    public String createNews(@RequestBody Actualitees news) {
        return actualiteesService.createOrUpdateNews(news);
    }

    @PutMapping("/Actualitees/UpdateActualitees/{id}")
    public String updateNews(@PathVariable Long id, @RequestBody Actualitees news) {
        news.setId(id);
        return actualiteesService.createOrUpdateNews(news);
    }

    @DeleteMapping("/delete/Actualitees/{id}")
    public void deleteNews(@PathVariable Long id) {
        actualiteesService.deleteNews(id);
    }
    @DeleteMapping("/delete/admin/{id}/{desc}")
    public String deleteAdmin(@PathVariable int id,@PathVariable String desc){
        return service.deleteAdmin(id,desc);
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

    ///////// Matiere ////////////////
    @PostMapping ("/add-Matiere")
    public String addMatiere (@RequestBody Matiere matiere){
        return matiereService.addMatiere(matiere);
    }

    @PutMapping("/update-Matiere")
    public MatiereDTO updateMatiere(@RequestBody MatiereDTO matiere){
        return matiereService.updateMatiere(matiere);
    }

    @DeleteMapping("/delete-Matiere/{idMatiere}")
    public String deleteMatiereById (@PathVariable int idMatiere){
        return matiereService.deleteMatiere(idMatiere);
    }

    @DeleteMapping("/delete-matiere/{libMatiere}")
    public String deleteMatiereByLib(@PathVariable String libMatiere){
        return matiereService.deleteMatiereByLib(libMatiere);
    }

    @GetMapping("/getAll-Matiere")
    public List<MatiereDTO> getAllMatiere(){
        return matiereService.getAllMatiere();
    }
    @GetMapping("/get-Matiere/{lib}")
        public Optional <Matiere> getMatiereByLib(@PathVariable String lib){
            return matiereService.getMatiereBylib(lib);
    }

    @GetMapping("/get-Matiere/{id}")
    public Optional<Matiere> getMatiereById(int id ){
        return matiereService.getMatiereById(id);
    }


    /////// Demande /////////

    @GetMapping("/Demande/getAllDemande")
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/Demande/getDemandeById/{id}")
    public ResponseEntity<DemandeDTO> getDemandeById(@PathVariable Long id) {
        DemandeDTO demande = demandeService.getDemandeById(id);
        return new ResponseEntity<>(demande, HttpStatus.OK);
    }

    @PutMapping("/Demande/updateDemandeStat/{id}/{stat}")
    public ResponseEntity<DemandeDTO> updateDemandeTraiter(@PathVariable Long id, @PathVariable String stat) {
        DemandeDTO updatedDemandeStat = demandeService.updateDemandeTraieter(id, stat);
        return new ResponseEntity<>(updatedDemandeStat, HttpStatus.OK);
    }

    @DeleteMapping("/Demande/DeleteDemande/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
