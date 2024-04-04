package com.springboot.MiniProject.serivce.Matiere_Service;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class MatiereService implements MatiereInterface{
    @Autowired
    private MatiereRepository matiereRepository ;
    @Autowired
    private GroupeRepository groupeRepository;
    public String addMatiere(Matiere mat){
        boolean MatiereExists =matiereRepository
                .findByLibelleMatiere(mat.getLibelleMatiere())
                .isPresent();
        if(MatiereExists){
            throw new IllegalStateException("Matiere deja exist");
        }else {
            matiereRepository.save(mat);
            return "Matiere added to the system";
        }
    }
    public String deleteMatiere(int id){
        matiereRepository.deleteById(id);
        return "Matiere Deleted Successfully !";
    }
    public String deleteMatiereByLib(String lib){
        Optional<Matiere> mat = matiereRepository.findByLibelleMatiere(lib);
        matiereRepository.deleteById(mat.get().getId());
        return "Enseignant Deleted Successfully !";
    }
    @Override
    public List<MatiereDTO> getAllMatiere() {
        List <Matiere> matieres =matiereRepository.findAll();
        List<MatiereDTO> matiereDTOS = new ArrayList<>();
        for (Matiere matiere : matieres) {
                MatiereDTO matiereDTO = getMatiereDTO(matiere);
                // Add the DTO to the list
            matiereDTOS.add(matiereDTO);

        }
        return matiereDTOS;
    }
   /* public Optional<List<Matiere>> getMatiereByEns(int id) {
        return  matiereRepository.findByEnseignantId(id);
    }*/
    public Optional<Matiere> getMatiereBylib(String lib) {
        return  matiereRepository.findByLibelleMatiere(lib);
    }
    public Optional<Matiere> getMatiereById(int id) {
        return  matiereRepository.findById(id);
    }

    @Override
    public MatiereDTO updateMatiere(MatiereDTO mat) {
        Optional<Matiere> MatiereExistOp = matiereRepository.findById(mat.getId());
        if(MatiereExistOp.isPresent()){
            Matiere matiereExist= MatiereExistOp.get();
            matiereExist.setCoefMat(mat.getCoefMat());
            matiereExist.setLibelleMatiere(mat.getLibelleMatiere());
            matiereRepository.save(matiereExist);
            MatiereDTO matiereDTO = getMatiereDTO(matiereExist);
            return matiereDTO;
        }
        else throw new IllegalArgumentException("Exception in updating Matiere !");
    }
    private static MatiereDTO getMatiereDTO(Matiere matiere) {
        MatiereDTO matiereDTO = new MatiereDTO();
        matiereDTO.setLibelleMatiere(matiere.getLibelleMatiere());
        matiereDTO.setCoefMat(matiere.getCoefMat());
        return matiereDTO;
    }
   /* public Optional<List<Matiere>> getMatiereBygrp(int id) {
        return  matiereRepository.findByGroupe(id);
    }*/

    public void ajouterMatiereAuGroupeParId(int idMatiere, int idGroupe) {
        Matiere matiere = matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new NotFoundException("Matiere not found with id: " + idMatiere));

        Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe not found with id: " + idGroupe));

        if (groupe.getMatieres() == null) {
            groupe.setMatieres(new ArrayList<>());
        }

        groupe.getMatieres().add(matiere);
        groupeRepository.save(groupe);
    }


    public void ajouterMatiereAChaqueGroupeDeFiliere(String filiere, int idMatiere) {
        List<Groupe> groupes = groupeRepository.findByFiliere(filiere);
        Matiere matiere = matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new NotFoundException("Matiere not found with id: " + idMatiere));

        for (Groupe groupe : groupes) {
            if (groupe.getMatieres() == null) {
                groupe.setMatieres(new ArrayList<>());
            }
            groupe.getMatieres().add(matiere);
            groupeRepository.save(groupe);
        }
    }
}
