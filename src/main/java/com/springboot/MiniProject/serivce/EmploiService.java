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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String ajouterEmploi(String datestr, boolean estEnseignant, int enseignantId, int groupeId, int filiereId, MultipartFile pdfFile) throws IOException, ParseException {
  /*      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(date);
*/
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

       /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(datestr);
        Emploi emploi = new Emploi();
        emploi.setDate(date);
        emploi.setEstEnseignant(estEnseignant);
        emploi.setPdfContenu(pdfFile.getBytes());

        if (estEnseignant) {
            Enseignant enseignant = new Enseignant();
            enseignant.setId(enseignantId);
            emploi.setEnseignant(enseignant);
        } else {
            Groupe groupe = new Groupe();
            groupe.setId(groupeId);
            emploi.setGroupe(groupe);
            Filiere filiere = new Filiere();
            filiere = filiereRepository.findById(filiereId).orElse(null);
            emploi.setFiliere(filiere);
        }

        return emploiRepository.save(emploi);*/
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


    public List<Emploi> trouverEmploisParFiliere(int filiereId) {
        return emploiRepository.findByFiliere_Id(filiereId);
    }

    public List<Emploi> trouverEmploisParNomFiliere(String nomFiliere) {
        return emploiRepository.findByFiliere_Nom(nomFiliere);
    }


    public Emploi trouverEmploisEtudiantsParGroupeId(int groupeId) {
        return emploiRepository.findByGroupe_Id(groupeId);
    }

    public Emploi trouverEmploisEnseignantParId(int enseignantId) {
        return emploiRepository.findByEnseignant_Id(enseignantId);
    }

    public Emploi mettreAJourEmploi(int emploiId, String nouvelleDate, MultipartFile  pdfFile) throws IOException, ParseException {
        Emploi emploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new NotFoundException("Emploi non trouvé avec l'ID : " + emploiId));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(nouvelleDate);
        emploi.setDate(date);
        emploi.setPdfContenu(pdfFile.getBytes());
        return emploiRepository.save(emploi);
    }
}

