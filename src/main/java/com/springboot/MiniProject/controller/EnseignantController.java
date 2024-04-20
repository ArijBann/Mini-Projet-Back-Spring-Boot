package com.springboot.MiniProject.controller;

import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.serivce.*;
import com.springboot.MiniProject.utils.PDFUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/issatso/enseignant")
public class EnseignantController {

    @Autowired
    private UserService service;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private ActualiteesService actualiteesService;
    @Autowired
    private EmploiService emploiService;

    @Autowired
    private SupportCoursService supportCoursService;
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

    /////Demande ////
    @GetMapping("/Demande/getAllDemande")
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
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



    ///////////Actualitees //////////////
    @GetMapping("/Actualitee/{targetAudiance}")
    public ResponseEntity<List<Actualitees>> getActualiteeEnseignant(@PathVariable String targetAudiance ) {
        List<Actualitees> news =actualiteesService.getNewsByTargetAudiance(targetAudiance);
        return ResponseEntity.ok(news);
    }


    ////////////// Matieres ////////////////
    @GetMapping("/Matiere/AllMatiere/{id}")
    public List<MatiereEnsDTO> getMatieresEnseigneesParEnseignant(@PathVariable int id ) {
        List<MatiereEnsDTO> mesMat = enseignantService.getMatieresEnseigneesParEnseignant(id);
        return new ResponseEntity<>(mesMat, HttpStatus.OK).getBody();
    }

    @GetMapping("/Matiere/Allgroupes/{id}")
    public List<GroupEnsDTO> getGroupesEnseignesParEnseignant(@PathVariable int id ) {
        List<GroupEnsDTO> mesGrp = enseignantService.getGroupesEnseignesParEnseignant(id);
        return new ResponseEntity<>(mesGrp, HttpStatus.OK).getBody();
    }

    //////////////// Emploi //////////////

    @GetMapping("/emploiContenuTes/{ensId}")
    public ResponseEntity<?> downloadEmploiIdEns(@PathVariable int ensId) throws IOException {
        byte[] emploipdf=emploiService.downloadEmploiIdEns(ensId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(emploipdf);

    }

////////// support de cours  /////////////
    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterSupportCours(@RequestParam String libelleSupport,
                                                      @RequestParam int idMatiere,
                                                      @RequestParam MultipartFile fichier) throws IOException {
        String message = supportCoursService.ajouterSupportCours(libelleSupport, idMatiere, fichier);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


  /*  @GetMapping("/par-matiere/{idMatiere}")
    public ResponseEntity<List<SupportCours>> consulterSupportsCoursParMatiere(@PathVariable int idMatiere) throws IOException {
        List<SupportCours> supportsCours = supportCoursService.consulterSupportsCoursParMatiere(idMatiere);
        return ResponseEntity.ok(supportsCours);
    }
*/

   /* @GetMapping("/supportsCoursParMatiere/{matiereId}")
    public ResponseEntity<byte[]> consulterSupportsCoursParMatiere(@PathVariable int matiereId) throws IOException {
        List<SupportCours> supportsCours = supportCoursService.consulterSupportsCoursParMatiere(matiereId);

        ByteArrayOutputStream
                outputStream = new ByteArrayOutputStream();
        boolean isFirst = true;

        for (SupportCours supportCours : supportsCours) {
            if (!isFirst) {
                // Ajouter une page blanche entre chaque contenu PDF
                outputStream.write(PDFUtil.createBlankPage());
            } else {
                isFirst = false;
            }

            byte[] pdfContenu = supportCours.getFichier();
            outputStream.write(pdfContenu);
        }

        byte[] pdfConcatene = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "supports_cours.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfConcatene);
    }
*/
  /* @GetMapping("/supportsCoursParMatiere/{matiereId}")
   public ResponseEntity<byte[]> consulterSupportsCoursParMatiere(@PathVariable int matiereId) throws IOException {
       List<SupportCours> supportsCours = supportCoursService.consulterSupportsCoursParMatiere(matiereId);

       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       boolean isFirst = true;

       for (SupportCours supportCours : supportsCours) {
           if (!isFirst) {
               // Add a blank page between each PDF content
               outputStream.write(PDFUtil.createBlankPage());
           } else {
               isFirst = false;
           }

           byte[] pdfContenu = supportCours.getFichier(); // Assuming this method returns the PDF content as byte[]
           outputStream.write(pdfContenu);
           outputStream.write(PDFUtil.createBlankPage()); // Add another blank page after each PDF content
       }

       byte[] pdfConcatene = outputStream.toByteArray();

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_PDF);
       headers.setContentDispositionFormData("filename", "supports_cours.pdf");

       return ResponseEntity.ok()
               .headers(headers)
               .body(pdfConcatene);
   }*/

   /* @GetMapping("/supportsCoursParMatiere/{matiereId}")
    public ResponseEntity<?> consulterSupportsCoursParMatiere(@PathVariable int matiereId) throws IOException {
        List<byte[]> supportsCours = supportCoursService.consulterSupportsCoursParMatiere(matiereId);
        System.out.println("support cours size %d " + supportsCours.size());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (int i = 0; i < supportsCours.size(); i++) {
            if (i != 0) {
                // Add a blank page between each PDF content
                outputStream.write(PDFUtil.createBlankPage());
            }
            outputStream.write(supportsCours.get(i));
            System.out.println("outputStream size %d " +outputStream.size());
        }

        byte[] pdfConcatene = outputStream.toByteArray();
        System.out.println("pdfConcatene length %d " +pdfConcatene.length);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfConcatene);
    }*/

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

    @GetMapping("/supportsCours/parFiliereEtNiveau")
    public ResponseEntity<List<SupportCoursDTO>> consulterSupportsCoursParFiliereEtNiveau(@RequestParam int idFiliere, @RequestParam String niveau) throws IOException {
        try {
            // Appelez la méthode pour récupérer les supports de cours par matière
            List<SupportCoursDTO> supportsCours = supportCoursService.trouverSupportsCoursParFiliereEtNiveau(idFiliere, niveau);
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


}