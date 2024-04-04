package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.dto.GroupeDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtudiantService {
    @Autowired
    EtudRepository etudiantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private MatiereRepository matiereRepository ;

    @Autowired
    private EtudMatiereRepository etudMatiereRepository ;
    public EtudiantDTO updateEtudiantInfo(EtudiantDTO etudiantDTO){
        User userExist = userRepository.findUserByEtudiantId(etudiantDTO.getIdEtudiant());
        userExist.setNumtel(etudiantDTO.getNumtel());
        userRepository.save(userExist);
        return etudiantDTO;
    }


    //////// remplissage de etudmatiere ///////
    public void remplirEtudMatierePourTousGroupes() {
        List<Groupe> groupes = groupeRepository.findAll();
        for (Groupe groupe : groupes) {
            remplirEtudMatiereParGroupe(groupe.getId());
        }
    }
    private void remplirEtudMatiereParGroupe(int idGroupe) {
        Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe not found with id: " + idGroupe));
        List<Etudiant> etudiants = etudiantRepository.findByGroupeId(idGroupe);
        List<Matiere> matieres = groupe.getMatieres();
        for (Etudiant etudiant : etudiants) {
            for (Matiere matiere : matieres) {
                EtudMatiere etudMatiere = new EtudMatiere();
                etudMatiere.setEtudiant(etudiant);
                etudMatiere.setMatiere(matiere);
                etudMatiereRepository.save(etudMatiere);
            }
        }
    }

    //////// gets matiere ///////

    private  static MatiereDTO getMatiereDTO(Matiere matiere) {
        MatiereDTO matiereDTO = new MatiereDTO();
        matiereDTO.setLibelleMatiere(matiere.getLibelleMatiere());
        matiereDTO.setCoefMat(matiere.getCoefMat());
        matiereDTO.setId(matiere.getId());
        List<Long> listensmat = new ArrayList<>();
        for (EnseignantMatiere enseignant : matiere.getEnseignantMatieres()) {
           listensmat.add(enseignant.getId());
        }
        matiereDTO.setIdEnseignantMatieres(listensmat);
        List<Long> listetudmat = new ArrayList<>();
        for (EtudMatiere etudmat : matiere.getEtudMatieres()) {
            listetudmat.add(etudmat.getId());
        }
        matiereDTO.setIdEtudMatieres(listetudmat);
        List<Integer> listidgrp = new ArrayList<>();
        for (Groupe grp : matiere.getGroupes()) {
            listidgrp.add(grp.getId());
        }
        matiereDTO.setIdGroupes(listidgrp);
        return matiereDTO;
    }

    //////// gets ense ///////

    private EnseignantDTO getEnseignantDTO (Enseignant ens) {
        User user = userRepository.findUserByEnseignantId(ens.getId());
        EnseignantDTO ensDTO = new EnseignantDTO();
        ensDTO.setIdEnseignant(ens.getId());
        ensDTO.setNom(user.getNom());
        ensDTO.setPrenom(user.getPrenom());
        ensDTO.setNumtel(user.getNumtel());
        ensDTO.setDiplome(ens.getDiplome());
        ensDTO.setNum_prof(ens.getNumProf());
        ensDTO.setEmail(user.getEmail());
        return ensDTO;
    }

    ///get tous les matieres pour etud a partire de son grp //
    public List<MatiereDTO> getMatieresDansGroupePourEtudiant(int idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new NotFoundException("Etudiant not found with id: " + idEtudiant));
        Groupe groupeEtudiant = etudiant.getGroupe();
        if (groupeEtudiant == null) {
            throw new NotFoundException("Aucun groupe trouvé pour l'étudiant avec l'ID: " + idEtudiant);
        }
        List<Matiere> matieresDansGroupe = groupeEtudiant.getMatieres();
        List<MatiereDTO> matieresDTOResult = new ArrayList<>();
        for (Matiere matiere : matieresDansGroupe) {
            matieresDTOResult.add(getMatiereDTO(matiere));
        }
        return matieresDTOResult;
    }


    ///get grp pour etud//
   /* public Groupe getGroupesEtudiant(int idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new NotFoundException("Étudiant non trouvé avec l'identifiant : " + idEtudiant));
        return etudiant.getGroupe();
    }*/
    public GroupeDTO getGroupeEtudiant(int idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new NotFoundException("Etudiant not found with id: " + idEtudiant));
        Groupe groupeEtudiant = etudiant.getGroupe();
        if (groupeEtudiant == null) {
            throw new NotFoundException("Aucun groupe trouvé pour l'étudiant avec l'ID: " + idEtudiant);
        }
        return convertToGroupeDTO(groupeEtudiant);
    }

    private GroupeDTO convertToGroupeDTO(Groupe groupe) {
        GroupeDTO groupeDTO = new GroupeDTO();
        // Remplir les champs de GroupeDTO à partir de l'objet Groupe
        groupeDTO.setId(groupe.getId());
        groupeDTO.setNiveau(groupe.getNiveau());
        groupeDTO.setFiliere(groupe.getFiliere().getNom());
        groupeDTO.setNumeroGroupe(groupe.getNumeroGroupe());
        List<EnseignantDTO> enseignantdto = new ArrayList<>();
        for (Enseignant enseignant : groupe.getEnseignants()) {
            enseignantdto.add(getEnseignantDTO(enseignant));
        }
        groupeDTO.setEnseignants(enseignantdto);
        List<MatiereDTO> matieresDTO = new ArrayList<>();
        for (Matiere matiere : groupe.getMatieres()) {
            matieresDTO.add(getMatiereDTO(matiere));
        }
        groupeDTO.setMatieres(matieresDTO);
        // Assurez-vous de remplir les autres champs selon vos besoins
        return groupeDTO;
    }
}