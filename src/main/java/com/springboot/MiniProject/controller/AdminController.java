package com.springboot.MiniProject.controller;



import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.Emploi;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.serivce.*;
import com.springboot.MiniProject.serivce.Matiere_Service.MatiereService;
import com.springboot.MiniProject.utils.PDFCompressionUtils;
import com.springboot.MiniProject.utils.PDFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
    @Autowired
    private  GroupeService groupeService;
    @Autowired
    private  EtudiantService etudiantService;
    @Autowired
    private EmploiService emploiService;
    @Autowired
    private SupportCoursService supportCoursService;

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

//////////// Enseignant ///////
    @GetMapping("/allEnseignants")
    public List<EnseignantDTO> getAllEnseignants() {
        return service.findAllEnseignant();
    }
    @GetMapping("/allEnseignantsInDepartement/{departement}")
    public List<EnseignantDTO> getAllEnseignantsByIdDepartement(@PathVariable long departement) {
        return service.findByIdDepartement(departement);
    }
    @GetMapping("/allEtudiantByFiliere/{filiere}")
    public List<EtudiantDTO> getEtudiantsByFiliere(@PathVariable String filiere) {
        return etudiantService.getEtudiantsByFiliere(filiere);
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

    @PostMapping ("/assignerEnseignantAuGroupe/{idEnseignant}/{idGroupe}")
    public void assignerEnseignantAuGroupe (@PathVariable int idEnseignant, @PathVariable int idGroupe){
        groupeService.assignerEnseignantAuGroupe(idEnseignant,idGroupe);
    }

//////////// Etudiant /////////////////
    @GetMapping("/getEtudiantByInscrit/{numInscri}")
    public ResponseEntity<EtudiantDTO> getEtudiantByInscri(@PathVariable double numInscri) {
        EtudiantDTO userEtudiantDTO = service.findByNumInscri(numInscri);
        if (userEtudiantDTO != null) {
            return ResponseEntity.ok(userEtudiantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/add-etud-matiere")
    public void remplirEtudMatierePourTousGroupes (){
         etudiantService.remplirEtudMatierePourTousGroupes();
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

    @PostMapping ("/add-Matiere-a-un-groupe-id/{idmatiere}/{idgrp}")
    public void  ajouterMatiereAuGroupeParId (@PathVariable int idmatiere ,@PathVariable int idgrp ){
        groupeService.ajouterMatiereAuGroupeParId(idmatiere,idgrp);
    }
    @PostMapping ("/add-Matiere-a-un-groupe-filiere/{filiere}/{idMatiere}")
    public void  ajouterMatiereAChaqueGroupeDeFiliere(@PathVariable String filiere ,@PathVariable int idMatiere ){
        groupeService.ajouterMatiereAChaqueGroupeDeFiliere(filiere , idMatiere);
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

//////////////Emploi de temps ////////////

    @PostMapping("/AddEmploi")
    public ResponseEntity<?> ajouterEmploi(@RequestParam String date,
                                                @RequestParam boolean estEnseignant,
                                                @RequestParam(required = false) int enseignantId,
                                                @RequestParam(required = false) int groupeId,
                                                @RequestParam int filiereId,
                                                @RequestParam MultipartFile pdfContenu) throws IOException, ParseException {
        String emploi = emploiService.ajouterEmploi(date, estEnseignant, enseignantId, groupeId, filiereId, pdfContenu);
        return ResponseEntity.status(HttpStatus.OK)
                .body(emploi);
    }




    @GetMapping("/emploiContenuTes/{ensId}")
	public ResponseEntity<?> downloadEmploiIdEns(@PathVariable int ensId) throws IOException {
		byte[] emploipdf=emploiService.downloadEmploiIdEns(ensId);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_PDF)
				.body(emploipdf);

	}

    @GetMapping("/emploisParIdFiliere/{idFiliere}")
    public ResponseEntity<List<EmploiDTO>> trouverEmploisParIdFiliere(@PathVariable int idFiliere) {
        try {
            // Appelez la méthode pour récupérer les emplois par idFiliere
            List<EmploiDTO> emplois = emploiService.trouverEmploisParIdFiliere(idFiliere);
            return ResponseEntity.ok(emplois);
        } catch (IOException e) {
            // Gérez l'exception en renvoyant une réponse HTTP appropriée
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des emplois", e);
        }
    }
    @GetMapping("/emploisParNomFiliere/{nomFiliere}")
    public ResponseEntity<List<EmploiDTO>> trouverEmploisParNomFiliere(@PathVariable String nomFiliere) {
        try {
            // Appelez la méthode pour récupérer les emplois par nomFiliere
            List<EmploiDTO> emplois = emploiService.trouverEmploisParNomFiliere(nomFiliere);
            return ResponseEntity.ok(emplois);
        } catch (IOException e) {
            // Gérez l'exception en renvoyant une réponse HTTP appropriée
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des emplois", e);
        }
    }

    @GetMapping("/emploiByLien/{idemp}/{lien}")
    public ResponseEntity<?> downloademps(@PathVariable int idemp,@PathVariable String lien ) throws IOException {
        byte[] supportpdf=emploiService.downloademps(lien,idemp);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(supportpdf);

    }

   /* @GetMapping("/Emploiidfiliere/{filiereId}")
    public ResponseEntity<List<?>> trouverEmploisParFiliere(@PathVariable int filiereId) throws IOException {
        List<Emploi> emploipdf = emploiService.trouverEmploisParFiliere(filiereId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(emploipdf);

    }

    @GetMapping("/Emploifiliere/{filiereNom}")
    public ResponseEntity<List<?>> trouverEmploisParNomFiliere(@PathVariable String filiereNom) throws IOException {
        List<Emploi> emploipdf = emploiService.trouverEmploisParNomFiliere(filiereNom);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(emploipdf);
    }*/

  /*  @GetMapping("/Emploiidfiliere/{filiereId}")
    public ResponseEntity<byte[]> trouverEmploisParFiliere(@PathVariable int filiereId) throws IOException {
        List<EmploiDTO> emplois = emploiService.trouverEmploisParIdFiliere(filiereId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean isFirst = true;

        for (Emploi emploi : emplois) {
            if (!isFirst) {
                // Ajouter une page blanche entre chaque contenu PDF
                outputStream.write(PDFUtil.createBlankPage());
            } else {
                isFirst = false;
            }

            byte[] pdfContenu = PDFCompressionUtils.decompressPDF(emploi.getPdfContenu());
            outputStream.write(pdfContenu);
        }

        byte[] pdfConcatene = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "emplois.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfConcatene);
    }

    @GetMapping("/Emploifiliere/{filiereNom}")
    public ResponseEntity<byte[]> trouverEmploisParNomFiliere(@PathVariable String filiereNom) throws IOException {
        List<EmploiDTO> emplois = emploiService.trouverEmploisParNomFiliere(filiereNom);

        // Concaténer les contenus PDF de tous les emplois
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Emploi emploi : emplois) {
            outputStream.write(emploi.getPdfContenu());
        }

        // Renvoyer la réponse avec le contenu PDF
        byte[] pdfConcatene = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "emplois.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfConcatene);
    }
*/

    @PutMapping("/EmploiUpdate/{id}")
    public ResponseEntity<?> mettreAJourEmploi(
            @RequestParam String nouvelleDate,
            @RequestParam MultipartFile nouveauContenuPDF,
            @PathVariable int id
    ) throws IOException, ParseException {
        String emploi = emploiService.mettreAJourEmploi(id, nouvelleDate, nouveauContenuPDF);
        return ResponseEntity.status(HttpStatus.OK)
                .body(emploi);
    }

    @DeleteMapping("/deleteEmploi/{id}")
    public String deleteEmploi ( @PathVariable int id){
        return emploiService.deleteEmploi(id);
    }
}
