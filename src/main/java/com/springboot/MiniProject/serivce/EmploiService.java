package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.entity.Emploi;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Filiere;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.utils.PDFCompressionUtils;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.EmploiRepository;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.FiliereRepository;
import com.springboot.MiniProject.repository.GroupeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmploiService {

    @Autowired
    private EmploiRepository emploiRepository;
    @Autowired
    private FiliereRepository filiereRepository ;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
 /* public String ajouterEmploi(String datestr, boolean estEnseignant, int enseignantId, int groupeId, int filiereId, MultipartFile pdfFile) throws IOException, ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(datestr);
        Emploi emp = emploiRepository.save(Emploi.builder().
                date(date).
                estEnseignant(estEnseignant).
                groupe(groupeRepository.findById(groupeId).orElse(null)).
                filiere(filiereRepository.findById(filiereId).orElse(null)).
                enseignant(enseignantRepository.findById(enseignantId).orElse(null)).
                pdfContenu(PDFCompressionUtils.compressPDF(pdfFile.getBytes())).build());
        if(emp != null){
            return "file uploaded successfully: " + pdfFile.getOriginalFilename();
        }
        return null;
    }


      public byte[] downloadEmploi(int groupeId) throws IOException {
        Emploi dbEmploi = emploiRepository.findByGroupe_Id(groupeId);
        //byte[] emploi=dbEmploi.getPdfContenu();
          byte[] emploi=PDFCompressionUtils.decompressPDF(dbEmploi.getPdfContenu());
        return emploi;
    }

    public byte[] downloadEmploiens(int ensId) throws IOException {
        Emploi dbEmploi = emploiRepository.findByEnseignant_Id(ensId);
        //byte[] emploi=dbEmploi.getPdfContenu();
        byte[] emploi=PDFCompressionUtils.decompressPDF(dbEmploi.getPdfContenu());
        return emploi;
    }
*/
 public String ajouterEmploi(String datestr, boolean estEnseignant, int enseignantId, int groupeId, int filiereId, MultipartFile pdfFile) throws IOException, ParseException {
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     Date date = formatter.parse(datestr);
     Enseignant enseignant = enseignantRepository.findById(enseignantId).orElse(null);
     Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
     Filiere filiere = filiereRepository.findById(filiereId).orElse(null);
     // Compresser le fichier PDF
     byte[] pdfContenu = PDFCompressionUtils.compressPDF(pdfFile.getBytes());
     if (enseignant == null && estEnseignant) {
         throw new NotFoundException("Enseignant non trouvé avec l'ID : " + enseignantId);
     }
     if (groupe == null && !estEnseignant) {
         throw new NotFoundException("Groupe non trouvé avec l'ID : " + groupeId);
     }
     if (filiere == null && !estEnseignant) {
         throw new NotFoundException("Filière non trouvée avec l'ID : " + filiereId);
     }
     if (emploiRepository.findByGroupe_Id(groupeId) != null || emploiRepository.findByEnseignant_Id(enseignantId) != null){
         throw new NotFoundException("emploi deja existe vous pouvez seulement le modifier ou supprimer !");

     }
     Emploi emp = emploiRepository.save(Emploi.builder()
             .date(date)
             .estEnseignant(estEnseignant)
             .enseignant(enseignant)
             .groupe(groupe)
             .filiere(filiere)
             .pdfContenu(pdfContenu)
             .build());

     if (emp != null) {
         return "Fichier téléversé avec succès : " + pdfFile.getOriginalFilename();
     }
     return null;
 }

 /*@Transactional(rollbackFor = Exception.class)
 public String ajouterEmploi(String datestr, boolean estEnseignant, int enseignantId, int groupeId, int filiereId, MultipartFile pdfFile) throws IOException, ParseException {
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     Date date = formatter.parse(datestr);

     // Récupérer les entités associées
     Enseignant enseignant = enseignantRepository.findById(enseignantId).orElse(null);
     Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
     Filiere filiere = filiereRepository.findById(filiereId).orElse(null);

     // Vérifier si les entités sont null
     if (enseignant == null && estEnseignant) {
         throw new NotFoundException("Enseignant non trouvé avec l'ID : " + enseignantId);
     }
     if (groupe == null && !estEnseignant) {
         throw new NotFoundException("Groupe non trouvé avec l'ID : " + groupeId);
     }
     if (filiere == null && !estEnseignant) {
         throw new NotFoundException("Filière non trouvée avec l'ID : " + filiereId);
     }

     // Compresser le fichier PDF
     byte[] pdfContenu = PDFCompressionUtils.compressPDF(pdfFile.getBytes());

     // Sauvegarder l'emploi avec les données compressées
     Emploi emp = emploiRepository.save(Emploi.builder()
             .date(date)
             .estEnseignant(estEnseignant)
             .enseignant(enseignant)
             .groupe(groupe)
             .filiere(filiere)
             .pdfContenu(pdfContenu)
             .build());

     if (emp != null) {
         return "Fichier téléversé avec succès : " + pdfFile.getOriginalFilename();
     }
     return null;
 }*/

    public String deleteEmploi(int  id) {
        emploiRepository.deleteById(id);
        return "Emploi Deleted Successfully !";
    }
    public byte[] downloadEmploi(int groupeId) throws IOException {
        Emploi dbEmploi = emploiRepository.findByGroupe_Id(groupeId);
        return PDFCompressionUtils.decompressPDF(dbEmploi.getPdfContenu());
    }
    public byte[] downloadEmploiIdEns(int ensId) throws IOException {
        Emploi dbEmploi = emploiRepository.findByEnseignant_Id(ensId);
        return PDFCompressionUtils.decompressPDF(dbEmploi.getPdfContenu());
    }
    public List<Emploi> trouverEmploisParFiliere(int filiereId) throws IOException {
        List<Emploi> emplois = emploiRepository.findByFiliere_Id(filiereId);
        List<Emploi> emploisDecompresses = new ArrayList<>();

        for (Emploi emploi : emplois) {
            byte[] pdfContenuDecompressed = PDFCompressionUtils.decompressPDF(emploi.getPdfContenu());
            emploi.setPdfContenu(pdfContenuDecompressed);
            emploisDecompresses.add(emploi);
        }

        return emploisDecompresses;
    }


    public List<Emploi>  trouverEmploisParNomFiliere(String nomFiliere)  throws IOException {
        List<Emploi> emplois = emploiRepository.findByFiliere_Nom(nomFiliere);
        List<Emploi> emploisDecompresses = new ArrayList<>();

        for (Emploi emploi : emplois) {
            byte[] pdfContenuDecompressed = PDFCompressionUtils.decompressPDF(emploi.getPdfContenu());
            emploi.setPdfContenu(pdfContenuDecompressed);
            emploisDecompresses.add(emploi);
        }

        return emploisDecompresses;
    }
   /* public List<Emploi> trouverEmploisParNomFiliere(String nomFiliere) {
        return emploiRepository.findByFiliere_Nom(nomFiliere);
    }

*/
    public byte[] trouverEmploisEtudiantsParGroupeId(int groupeId) throws IOException {
        Emploi dbEmploi = emploiRepository.findByGroupe_Id(groupeId);
        return PDFCompressionUtils.decompressPDF(dbEmploi.getPdfContenu());
    }



    public String mettreAJourEmploi(int emploiId, String nouvelleDate, MultipartFile  pdfFile) throws IOException, ParseException {
        Emploi emploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new NotFoundException("Emploi non trouvé avec l'ID : " + emploiId));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(nouvelleDate);
        byte[] pdfContenu = PDFCompressionUtils.compressPDF(pdfFile.getBytes());
        emploi.setDate(date);
        emploi.setPdfContenu(pdfContenu);
        Emploi emp = emploiRepository.save(emploi);
        if (emp != null) {
            return "Fichier modifier avec succès : " + pdfFile.getOriginalFilename();
        }
        return null;
    }
}

