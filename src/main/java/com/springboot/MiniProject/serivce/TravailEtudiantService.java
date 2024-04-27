package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.TravailEtudiantDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.*;
import com.springboot.MiniProject.utils.PDFCompressionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravailEtudiantService {

    @Autowired
    private TravailEtudiantRepository travailEtudiantRepository;
    @Autowired
    private CompteRenduRepository compteRenduRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EtudRepository etudRepository;

    public List<TravailEtudiantDTO> getTravauxEtudiantByCompteRendu(int idcr) {
        List<TravailEtudiant> travauxEtudiants = travailEtudiantRepository.findByCompteRendu_Id(idcr);
        return convertToTravailEtudiantDTOList(travauxEtudiants);
    }

    public void addTravailEtudiant(int idCompteRendu, MultipartFile fichier, String message, int etudiantId) throws IOException {
        CompteRendu compteRendu = compteRenduRepository.findById(idCompteRendu)
                .orElseThrow(() -> new EntityNotFoundException("Compte rendu not found with id: " + idCompteRendu));
        Etudiant etudiant = etudRepository.findById(etudiantId)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant not found with id: " + etudiantId));
        byte[] pdfContenu = PDFCompressionUtils.compressPDF(fichier.getBytes());
        TravailEtudiant travailEtudiant = new TravailEtudiant();
        travailEtudiant.setFichier(pdfContenu);
        travailEtudiant.setMessage(message);
        travailEtudiant.setEtudiant(etudiant);
        travailEtudiant.setCompteRendu(compteRendu);
        travailEtudiantRepository.save(travailEtudiant);
    }

    public void deleteTravailEtudiant(int travailEtudiantId) {
        travailEtudiantRepository.deleteById(travailEtudiantId);
    }

    public void updateTravailEtudiant(int travailEtudiantId, TravailEtudiant updatedTravailEtudiant) {
        TravailEtudiant existingTravailEtudiant = travailEtudiantRepository.findById(travailEtudiantId)
                .orElseThrow(() -> new EntityNotFoundException("Travail étudiant not found"));

        existingTravailEtudiant.setFichier(updatedTravailEtudiant.getFichier());
        existingTravailEtudiant.setMessage(updatedTravailEtudiant.getMessage());

        travailEtudiantRepository.save(existingTravailEtudiant);
    }

    private List<TravailEtudiantDTO> convertToTravailEtudiantDTOList(List<TravailEtudiant> travauxEtudiants) {
        return travauxEtudiants.stream()
                .map(this::convertToTravailEtudiantDTO)
                .collect(Collectors.toList());
    }

    private TravailEtudiantDTO convertToTravailEtudiantDTO(TravailEtudiant travailEtudiant) {
        TravailEtudiantDTO travailEtudiantDTO = new TravailEtudiantDTO();
        User user =userRepository.findUserByEtudiantId(travailEtudiant.getEtudiant().getId());

        travailEtudiantDTO.setId(travailEtudiant.getId());
        travailEtudiantDTO.setMessage(travailEtudiant.getMessage());
        if (user != null){
            travailEtudiantDTO.setEtudiantNom(user.getNom());
        }else {
            travailEtudiantDTO.setEtudiantNom(null);

        }
        travailEtudiantDTO.setLienPdf(genererLienPDF(travailEtudiant.getId()));
        return travailEtudiantDTO;
    }

    private String genererLienPDF(int travailEtudiantId) {
        String cheminVersPDF = "/pdf/";
        String nomFichierPDF = "travail_etudiant_" + travailEtudiantId + ".pdf";
        return cheminVersPDF + nomFichierPDF;
    }


    public byte[] getTravailEtudiantByLien(String lien, int idTravailEtudiant) throws IOException {
        TravailEtudiantDTO travailEtudiantDTO = getTravailEtudiantDTOByLien(lien, idTravailEtudiant);
        TravailEtudiant trvEtud = travailEtudiantRepository.findById(idTravailEtudiant)
                .orElseThrow(() -> new NotFoundException("TravailEtudiant non trouvé avec l'ID : " + idTravailEtudiant));
        if (travailEtudiantDTO != null) {
            return PDFCompressionUtils.decompressPDF(trvEtud.getFichier());
        } else {
            return null;
        }
    }

    public TravailEtudiantDTO getTravailEtudiantDTOByLien(String lien, int idTravailEtudiant) {
        TravailEtudiant travailEtudiant = travailEtudiantRepository.findById(idTravailEtudiant)
                .orElseThrow(() -> new NotFoundException("Travail étudiant non trouvé avec l'ID : " + idTravailEtudiant));

        if (travailEtudiant != null) {
            return convertToTravailEtudiantDTO(travailEtudiant, lien);
        } else {
            return null;
        }
    }

    private TravailEtudiantDTO convertToTravailEtudiantDTO(TravailEtudiant travailEtudiant, String lien) {
        TravailEtudiantDTO travailEtudiantDTO = new TravailEtudiantDTO();
        User user = userRepository.findUserByEtudiantId(travailEtudiant.getEtudiant().getId());

        travailEtudiantDTO.setId(travailEtudiant.getId());
        travailEtudiantDTO.setLienPdf(lien);
        travailEtudiantDTO.setMessage(travailEtudiant.getMessage());
        if (user != null){
            travailEtudiantDTO.setEtudiantNom(user.getNom());
        }else {
            travailEtudiantDTO.setEtudiantNom(null);
        }
        return travailEtudiantDTO;
    }

}
