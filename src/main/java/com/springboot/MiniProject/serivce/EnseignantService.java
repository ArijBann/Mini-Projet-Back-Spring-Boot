package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.EtudRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnseignantService {
    @Autowired
    EnseignantRepository enseignantRepository;
    @Autowired
    UserRepository userRepository;
    public EnseignantDTO updateEtudiantInfo(EnseignantDTO enseignantDTO){
        User userExist = userRepository.findUserByEnseignantId(enseignantDTO.getIdEnseignant());
        userExist.setNumtel(enseignantDTO.getNumtel());
        userRepository.save(userExist);
        return enseignantDTO;
    }




    private static MatiereDTO getMatiereDTO(Matiere matiere) {
        MatiereDTO matiereDTO = new MatiereDTO();
        matiereDTO.setLibelleMatiere(matiere.getLibelleMatiere());
        matiereDTO.setCoefMat(matiere.getCoefMat());
        return matiereDTO;
    }

    private static MatiereEnsDTO getMatiereEnsDTO(Matiere matiere) {
        MatiereEnsDTO matiereEnsDTO = new MatiereEnsDTO();
        matiereEnsDTO.setId(matiere.getId());
        matiereEnsDTO.setLibelleMatiere(matiere.getLibelleMatiere());
        matiereEnsDTO.setCoefMat(matiere.getCoefMat());
        List<Integer> listidgrp = new ArrayList<>();
        for (Groupe grp : matiere.getGroupes()) {
            listidgrp.add(grp.getId());
        }
        matiereEnsDTO.setIdGroupes(listidgrp);
        return matiereEnsDTO;
    }
    public List<MatiereEnsDTO> getMatieresEnseigneesParEnseignant(int idEnseignant) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("Enseignant not found with id: " + idEnseignant));
        List<EnseignantMatiere> enseignantMatieres = enseignant.getEnseignantMatieres();
        List<MatiereEnsDTO> matieresDTOResult = new ArrayList<>();
        for (EnseignantMatiere enseignantMatiere : enseignantMatieres) {
            matieresDTOResult.add(getMatiereEnsDTO(enseignantMatiere.getMatiere()));
        }
        return matieresDTOResult;
    }

/*
    public List<Groupe> getGroupesEnseignesParEnseignant(int idEnseignant) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("Enseignant not found with id: " + idEnseignant));
        return enseignant.getGroupes();
    }
*/
  /*  public List<GroupeDTO> getGroupesEnseignesParEnseignant(int idEnseignant) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("Enseignant not found with id: " + idEnseignant));
        List<Groupe> groupes = enseignant.getGroupes();
        return groupes.stream()
                .map(this::convertToGroupeDTO)
                .collect(Collectors.toList());
    }

    private GroupeDTO convertToGroupeDTO(Groupe groupe) {
        GroupeDTO groupeDTO = new GroupeDTO();
        // Remplir les champs de GroupeDTO à partir de l'objet Groupe
        groupeDTO.setId(groupe.getId());
        groupeDTO.setNiveau(groupe.getNiveau());
        groupeDTO.setFiliere(groupe.getFiliere());
        groupeDTO.setNumeroGroupe(groupe.getNumeroGroupe());
        // Assurez-vous de remplir les autres champs selon vos besoins
        return groupeDTO;
    }

   */

    public List <GroupEnsDTO> getGroupesEnseignesParEnseignant(int idEnseignant) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("Enseignant not found with id: " + idEnseignant));
        List<Groupe> groupes = enseignant.getGroupes();
        if (groupes == null) {
            throw new NotFoundException("Aucun groupe trouvé pour l'Enseignant avec l'ID: " + idEnseignant);
        }
        return groupes.stream()
                .map(groupe -> convertToGroupeDTO(groupe, idEnseignant))
                .collect(Collectors.toList());
    }

    private GroupEnsDTO convertToGroupeDTO(Groupe groupe ,int idEnseignant) {
        GroupEnsDTO groupensDTO = new GroupEnsDTO();
        // Remplir les champs de GroupeDTO à partir de l'objet Groupe
        groupensDTO.setId(groupe.getId());
        groupensDTO.setNiveau(groupe.getNiveau());
        groupensDTO.setFiliere(groupe.getFiliere().getNom());
        groupensDTO.setNumeroGroupe(groupe.getNumeroGroupe());
        List<MatiereEnsDTO> matieresEnsDTO = new ArrayList<>();
        for (Matiere matiere : groupe.getMatieres()) {
            for (EnseignantMatiere enseignantMatiere : matiere.getEnseignantMatieres()) {
                if (enseignantMatiere.getEnseignant().getId() == idEnseignant) {
                    matieresEnsDTO.add(getMatiereEnsDTO(matiere));
                }
            }
        }
        groupensDTO.setMatieres(matieresEnsDTO);
        // Assurez-vous de remplir les autres champs selon vos besoins
        return groupensDTO;
    }
}