package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.CompteRenduDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.*;
import com.springboot.MiniProject.utils.PDFCompressionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompteRenduService {

    @Autowired
    private CompteRenduRepository compteRenduRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private EtudRepository etudRepository;
    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private UserRepository userRepository;
    public List<CompteRenduDTO> getComptesRendusByEnseignant(int enseignantId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new NotFoundException("enseignant non trouvé avec l'ID : " + enseignantId));
        if(enseignant != null){
        List<CompteRendu> comptesRendus = compteRenduRepository.findByEnseignant_Id(enseignantId);

        return convertToCompteRenduDTOList(comptesRendus);
        }else return null ;

    }


    public List<CompteRenduDTO> getComptesRendusByMatiereIdEns(int matiereId, int enseignantId) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new NotFoundException("Matiere non trouvé avec l'ID : " + matiereId));
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new NotFoundException("enseignant non trouvé avec l'ID : " + enseignantId));
        List<CompteRendu> comptesRendus = new ArrayList<>();
        if(matiere != null && enseignant != null) {
            List<CompteRendu> comptesRendusMat = compteRenduRepository.findByMatiere_Id(matiereId);
            List<CompteRendu> compteRendusEns = compteRenduRepository.findByEnseignant_Id(enseignantId);
            for( CompteRendu crmat : comptesRendusMat){
                for (CompteRendu crens : compteRendusEns) {
                    if (crmat.getId() == crens.getId()) {
                        comptesRendus.add(crmat);
                }
                }
            }
            return convertToCompteRenduDTOList(comptesRendus);
        }else return null ;
    }

     public List<CompteRenduDTO> getCompteRenduByFiliereNiveauGroupeMatiere(int idEnseignant, int idMatiere, int idGroupe) {
       List<CompteRendu> comptesRendusMatEns = new ArrayList<>();
       Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe non trouvé avec l'ID : " + idGroupe));
       Matiere matiere = matiereRepository.findById(idMatiere)
                 .orElseThrow(() -> new NotFoundException("Matiere non trouvé avec l'ID : " + idMatiere));
       Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                 .orElseThrow(() -> new NotFoundException("enseignant non trouvé avec l'ID : " + idEnseignant));
       List<CompteRendu> comptesRendus = new ArrayList<>();
       if(matiere != null && enseignant != null && groupe != null ) {
             List<CompteRendu> comptesRendusGrp = compteRenduRepository.findByGroupe_Id(idGroupe);
             List<CompteRendu> comptesRendusMat = compteRenduRepository.findByMatiere_Id(idMatiere);
             List<CompteRendu> compteRendusEns = compteRenduRepository.findByEnseignant_Id(idEnseignant);
             for( CompteRendu crmat : comptesRendusMat){
                 for (CompteRendu crens : compteRendusEns) {
                     if (crmat.getId() == crens.getId()) {
                         comptesRendusMatEns.add(crmat);
                     }
                 }
             }
            for( CompteRendu crgrp : comptesRendusGrp){
                    for (CompteRendu crmatens : comptesRendusMatEns) {
                        if (crgrp.getId() == crmatens.getId()) {
                            comptesRendus.add(crgrp);
                    }
                    }
                }
             return convertToCompteRenduDTOList(comptesRendus);
       }else return null ;
   }
  /*  public List<CompteRenduDTO> getCompteRenduByFiliereNiveauGroupe(String filiere, int groupeId) {

        Groupe grp = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new NotFoundException("groupe non trouvé avec l'ID : " + groupeId));
        if(grp != null){
        List<CompteRendu> comptesRendus = compteRenduRepository.findByMatiere_Filiere_NomAndMatiere_Groupes_Id(filiere, groupeId);
        return convertToCompteRenduDTOList(comptesRendus);
        }else return null ;
    }
*/
  /*  public List<CompteRenduDTO> getCompteRenduByFiliereNiveauGroupeMatiere(int idEnseignant, int idMatiere, int idGroupe, String niveau) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("enseignant non trouvé avec l'ID : " + idEnseignant));
        if(enseignant != null){
        List<CompteRendu> comptesRendus = compteRenduRepository.findByEnseignant_IdAndMatiere_IdAndMatiere_Groupes_IdAndMatiere_Groupes_Niveau(idEnseignant, idMatiere, idGroupe, niveau);
        return convertToCompteRenduDTOList(comptesRendus);
        }else return null ;
    }*/
   public byte[] getCompteRenduByLien(String lien, int idCompteRendu) throws IOException {
        CompteRenduDTO compteRenduDTO = getCompteRenduDTOByLien(lien, idCompteRendu);
        CompteRendu compteRendu = compteRenduRepository.findById(idCompteRendu)
                .orElseThrow(() -> new NotFoundException("Compte rendu non trouvé avec l'ID : " + idCompteRendu));
        if (compteRenduDTO != null) {
            return PDFCompressionUtils.decompressPDF(compteRendu.getFichier());
        } else {
            return null;
        }
    }

    public CompteRenduDTO getCompteRenduDTOByLien(String lien, int idCompteRendu) {
        CompteRendu compteRendu = compteRenduRepository.findById(idCompteRendu)
                .orElseThrow(() -> new NotFoundException("Compte rendu non trouvé avec l'ID : " + idCompteRendu));

        if (compteRendu != null) {
            return convertToCompteRenduDTO2(compteRendu, lien);
        } else {
            return null;
        }
    }

    private CompteRenduDTO convertToCompteRenduDTO2(CompteRendu compteRendu, String lien) {
        CompteRenduDTO compteRenduDTO = new CompteRenduDTO();
        User user = userRepository.findUserByEnseignantId(compteRendu.getEnseignant().getId());
        compteRenduDTO.setId(compteRendu.getId());
        compteRenduDTO.setTitre(compteRendu.getTitre());
        compteRenduDTO.setDescription(compteRendu.getDescription());
        compteRenduDTO.setDeadline(compteRendu.getDeadline());
        compteRenduDTO.setMatiereNom(compteRendu.getMatiere().getLibelleMatiere());
        if (user != null){
            compteRenduDTO.setEnseignantNom(user.getNom());
        }else {
            compteRenduDTO.setEnseignantNom(null);
        }
        compteRenduDTO.setLienPDF(lien);
        return compteRenduDTO;
    }
    public List<CompteRenduDTO> getComptesRendusByMatiere(int matiereId) {
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new NotFoundException("Matiere non trouvé avec l'ID : " + matiereId));

        if(matiere != null) {
            List<CompteRendu> comptesRendus = compteRenduRepository.findByMatiere_Id(matiereId);
            return convertToCompteRenduDTOList(comptesRendus);
        }else return null ;
    }

    public void addCompteRendu(int enseignantId, String titre, String description, MultipartFile fichier, String datestr, int matiereId , int groupeId) throws ParseException, IOException {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new NotFoundException("enseignant non trouvé avec l'ID : " + enseignantId));
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new NotFoundException("Groupe non trouvé avec l'ID : " + groupeId));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = formatter.parse(datestr);

        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new NotFoundException("Matiere non trouvé avec l'ID : " + matiereId));
        byte[] pdfContenu = PDFCompressionUtils.compressPDF(fichier.getBytes());
        CompteRendu compteRendu = new CompteRendu();
        compteRendu.setTitre(titre);
        compteRendu.setDescription(description);
        compteRendu.setFichier(pdfContenu);
        compteRendu.setDeadline(deadline);
        if (groupe != null )
        {compteRendu.setGroupe(groupe);}
        else {compteRendu.setGroupe(null);}
        if (enseignant != null )
        {compteRendu.setEnseignant(enseignant);}
        else {compteRendu.setEnseignant(null);}
        if (matiere != null ) {
            compteRendu.setMatiere(matiere);
        }else {
            compteRendu.setMatiere(null);

        }
        compteRenduRepository.save(compteRendu);
    }
    public void deleteCompteRendu(int compteRenduId) {
        compteRenduRepository.deleteById(compteRenduId);
    }

    public void updateCompteRendu(int compteRenduId, CompteRendu updatedCompteRendu) {
        CompteRendu existingCompteRendu = compteRenduRepository.findById(compteRenduId)
                .orElseThrow(() -> new EntityNotFoundException("Compte rendu not found"));

        existingCompteRendu.setTitre(updatedCompteRendu.getTitre());
        existingCompteRendu.setDescription(updatedCompteRendu.getDescription());
        existingCompteRendu.setFichier(updatedCompteRendu.getFichier());
        existingCompteRendu.setDeadline(updatedCompteRendu.getDeadline());

        compteRenduRepository.save(existingCompteRendu);
    }

    private List<CompteRenduDTO> convertToCompteRenduDTOList(List<CompteRendu> comptesRendus) {
        return comptesRendus.stream()
                .map(this::convertToCompteRenduDTO)
                .collect(Collectors.toList());
    }

    private CompteRenduDTO convertToCompteRenduDTO(CompteRendu compteRendu) {
        User user = userRepository.findUserByEnseignantId(compteRendu.getEnseignant().getId());
        String lienPDF = genererLienPDF(compteRendu.getId());compteRendu.getGroupe().getNumeroGroupe();
        String grp = compteRendu.getGroupe().getFiliere().getNom()+'-'+compteRendu.getGroupe().getNiveau()+'-';
        CompteRenduDTO dto = new CompteRenduDTO();
        dto.setId(compteRendu.getId());
        dto.setTitre(compteRendu.getTitre());
        dto.setDescription(compteRendu.getDescription());
        dto.setDeadline(compteRendu.getDeadline());
        dto.setGroupeNom(grp);
        dto.setLienPDF(lienPDF);
        dto.setMatiereNom(compteRendu.getMatiere().getLibelleMatiere());
        if (user != null){
            dto.setEnseignantNom(user.getNom());
        }else {
            dto.setEnseignantNom(null);
        }
        return dto;
    }

    private String genererLienPDF(int compteRenduId) {
        String cheminVersPDF = "/pdf/";
        String nomFichierPDF = "compte_rendu_" + compteRenduId + ".pdf";
        return cheminVersPDF + nomFichierPDF;
    }

}