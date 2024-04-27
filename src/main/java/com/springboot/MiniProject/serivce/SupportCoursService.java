package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.SupportCoursDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.GroupeRepository;
import com.springboot.MiniProject.repository.MatiereRepository;
import com.springboot.MiniProject.repository.SupportCoursRepository;
import com.springboot.MiniProject.utils.PDFCompressionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportCoursService implements SupportCoursInterface {
    @Autowired
    private SupportCoursRepository supportCoursRepository;
    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    public String ajouterSupportCours(String libelleSupport, int idMatiere, MultipartFile pdfFile) throws IOException {
        Matiere matiere = matiereRepository.findById(idMatiere).orElse(null);
        if (matiere == null) {
            throw new NotFoundException("Matiere non trouvée avec l'ID : " + idMatiere);
        }

        byte[] fichier = PDFCompressionUtils.compressPDF(pdfFile.getBytes());

        SupportCours supportCours = SupportCours.builder()
                .libelleSupport(libelleSupport)
                .fichier(fichier)
                .matiere(matiere)
                .build();

        SupportCours savedSupportCours = supportCoursRepository.save(supportCours);
        if (savedSupportCours != null) {
            return "Support de cours ajouté avec succès : " + pdfFile.getOriginalFilename();
        }
        return null;
    }

    public  List<byte[]> consulterSupportsCoursParMatiere(int idMatiere) throws IOException {
        Matiere matiere = matiereRepository.findById(idMatiere).orElse(null);
        if (matiere == null) {
            throw new NotFoundException("Matiere non trouvée avec l'ID : " + idMatiere);
        }

      /* List<SupportCours> supportsCours = matiere.getSupportsCours();
      //List<SupportCours> supportsCours2 = supportCoursRepository.findByMatiere_Id(matiere.getId());
        List<byte[]> supportsCoursDecompresses = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            byte[] fichierDecompressed = PDFCompressionUtils.decompressPDF(supportCours.getFichier());
            supportsCoursDecompresses.add(fichierDecompressed);
        }

        return supportsCoursDecompresses;*/
        List<SupportCours> supportsCours = supportCoursRepository.findByMatiere_Id(idMatiere);
        System.out.println(supportsCours.size());
        List<byte[]> supportsCoursDecompresses = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            System.out.println(supportCours.getLibelleSupport());
            byte[] fichierDecompressed = PDFCompressionUtils.decompressPDF(supportCours.getFichier());
            supportsCoursDecompresses.add(fichierDecompressed);
        }
        System.out.println(supportsCoursDecompresses.size());
        return supportsCoursDecompresses;

    }



    public List<SupportCoursDTO> consulterSupportsCoursParMatiere2(int idMatiere) throws IOException {
        Matiere matiere = matiereRepository.findById(idMatiere).orElse(null);
        if (matiere == null) {
            throw new NotFoundException("Matiere non trouvée avec l'ID : " + idMatiere);
        }

        List<SupportCours> supportsCours = supportCoursRepository.findByMatiere_Id(idMatiere);
        List<SupportCoursDTO> supportsCoursDTOs = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            byte[] fichierDecompressed = PDFCompressionUtils.decompressPDF(supportCours.getFichier());
            String lienPDF = genererLienPDF(supportCours.getId()); // Générer le lien ou le code
            SupportCoursDTO dto = new SupportCoursDTO();
            dto.setId(supportCours.getId());
            dto.setLibelleSupport(supportCours.getLibelleSupport());
            dto.setLienPDF(lienPDF);
            supportsCoursDTOs.add(dto);
        }
        return supportsCoursDTOs;
    }


    public byte[] downloadunsup(String lien, int idSup) throws IOException {
      SupportCoursDTO supDto = getSupportCoursByLien(lien ,idSup);
      SupportCours sup = supportCoursRepository.findById(idSup)
              .orElseThrow(() -> new NotFoundException("Emploi non trouvé avec l'ID : " + idSup));
      if (supDto != null)
      {  return PDFCompressionUtils.decompressPDF(sup.getFichier());
      }else  return null;
    }

    @Override
    public SupportCoursDTO getSupportCoursByLien(String lien , int idSup) {
       SupportCours supportCours =supportCoursRepository.findById(idSup)
               .orElseThrow(() -> new NotFoundException("Emploi non trouvé avec l'ID : " + idSup));

        if (supportCours != null)
        {  SupportCoursDTO supportCoursDTODTO = convertToSupportCoursDTO(supportCours,lien);
            return supportCoursDTODTO;
        }else return null;

    }

    private String genererLienPDF(int supportCoursId) {
        // Supposons que vos fichiers PDF sont stockés dans un répertoire nommé "pdf" à la racine de votre application
        String cheminVersPDF = "/pdf/";
        // Vous pouvez personnaliser le nom du fichier PDF en utilisant l'ID du support de cours
        String nomFichierPDF = "support_cours_" + supportCoursId + ".pdf";
        // Combiner le chemin vers le répertoire PDF avec le nom du fichier
        String lienPDF = cheminVersPDF + nomFichierPDF;
        return lienPDF;
    }
    /*public List<SupportCours> trouverSupportsCoursParGroupe(int idGroupe) throws IOException {
        Groupe groupe = groupeRepository.findById(idGroupe).orElse(null);
        if (groupe == null) {
            throw new NotFoundException("Groupe non trouvé avec l'ID : " + idGroupe);
        }

        List<SupportCours> supportsCours = new ArrayList<>();
        for (Matiere matiere : groupe.getMatieres()) {
            supportsCours.addAll(matiere.getSupportsCours());
        }

        List<SupportCours> supportsCoursDecompresses = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            byte[] fichierDecompressed = PDFCompressionUtils.decompressPDF(supportCours.getFichier());
            supportCours.setFichier(fichierDecompressed);
            supportsCoursDecompresses.add(supportCours);
        }

        return supportsCoursDecompresses;
    }

    public List<SupportCours> trouverSupportsCoursParFiliereEtNiveau(int idFiliere, String niveau) throws IOException {
        List<SupportCours> supportsCours = new ArrayList<>();
        for (Matiere matiere : matiereRepository.findByFiliereIdAndNiveau(idFiliere, niveau)) {
            supportsCours.addAll(matiere.getSupportsCours());
        }

        List<SupportCours> supportsCoursDecompresses = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            byte[] fichierDecompressed = PDFCompressionUtils.decompressPDF(supportCours.getFichier());
            supportCours.setFichier(fichierDecompressed);
            supportsCoursDecompresses.add(supportCours);
        }

        return supportsCoursDecompresses;
    }*/

    public List<SupportCoursDTO> trouverSupportsCoursParGroupe(int idGroupe) throws IOException {
        Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe non trouvé avec l'ID : " + idGroupe));
        List<SupportCours> supportsCours = new ArrayList<>();
        for (Matiere matiere : groupe.getMatieres()) {
            supportsCours.addAll(matiere.getSupportsCours());
        }
        return convertToSupportCoursDTOList(supportsCours);
    }

    public List<SupportCoursDTO> trouverSupportsCoursParFiliereEtNiveau(int idFiliere, String niveau) throws IOException {
        List<Matiere> matieres = matiereRepository.findByFiliereIdAndNiveau(idFiliere, niveau);
        List<SupportCours> supportsCours = new ArrayList<>();
        for (Matiere matiere : matieres) {
            supportsCours.addAll(matiere.getSupportsCours());
        }
        return convertToSupportCoursDTOList(supportsCours);
    }

    private List<SupportCoursDTO> convertToSupportCoursDTOList(List<SupportCours> supportsCours) {
        List<SupportCoursDTO> supportsCoursDTOs = new ArrayList<>();
        for (SupportCours supportCours : supportsCours) {
            String lienPDF = genererLienPDF(supportCours.getId());
            supportsCoursDTOs.add(convertToSupportCoursDTO(supportCours, lienPDF));
        }
        return supportsCoursDTOs;
    }
    private SupportCoursDTO convertToSupportCoursDTO(SupportCours sup, String lien) {
        SupportCoursDTO supportCoursDTO = new SupportCoursDTO();
        supportCoursDTO.setLibelleSupport(sup.getLibelleSupport());
        supportCoursDTO.setLienPDF(lien);
        supportCoursDTO.setId(sup.getId());
        return supportCoursDTO;
    }


}
