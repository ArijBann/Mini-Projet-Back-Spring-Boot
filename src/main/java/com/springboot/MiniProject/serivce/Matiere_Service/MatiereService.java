package com.springboot.MiniProject.serivce.Matiere_Service;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    /*public Matiere updateMatiere(Matiere mat){
        Matiere ExistingMatiere =matiereRepository
                .findById(mat.getId()).orElse(null);
        ExistingMatiere.setLibelleMatiere(mat.getLibelleMatiere());
        ExistingMatiere.setCoefMat(mat.getCoefMat());
        return matiereRepository.save(mat);
    }*/
    public String deleteMatiere(int id){
        matiereRepository.deleteById(id);
        return "Matiere Deleted Successfully !";
    }
    public String deleteMatiereByLib(String lib){
        Optional<Matiere> mat = matiereRepository.findByLibelleMatiere(lib);
        matiereRepository.deleteById(mat.get().getId());
        return "Enseignant Deleted Successfully !";
    }
    public List<Matiere> getAllMatiere() {
        return  matiereRepository.findAll();
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
}
