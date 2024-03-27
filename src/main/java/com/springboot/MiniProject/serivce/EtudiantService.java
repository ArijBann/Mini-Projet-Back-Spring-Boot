package com.springboot.MiniProject.serivce;

import org.springframework.stereotype.Service;

import com.springboot.MiniProject.dto.UserEtudiantDTO;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.EtudRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {
    @Autowired
    EtudRepository etudRepository;
    @Autowired
    UserRepository userRepository;
    public User updateEtudiant (User user){
        User existEtud = userRepository.findById(user.getId()).orElse(null);
        existEtud.setNumtel(user.getNumtel());
        existEtud.setPassword(user.getPassword());
        return userRepository.save(existEtud);
    }
    public List<Etudiant> getAllEtudiant (){
        return etudRepository.findAll();
    }

    public Etudiant getEtudiantByNumInscrit(int numInscrit){
        return etudRepository.findEtudiantByNum_inscri(numInscrit);
    }

    public List <Etudiant> getEtudiantByGroupe (int idGroupe){
        return etudRepository.findEtudiantByGroupe(idGroupe);
    }
    /*public String updateEtudiantAsAdmin(UserEtudiantDTO userEtudiantDTO){
        User userExist = userEtudiantDTO.getUser();
        Etudiant etudiantExist = userEtudiantDTO.getEtudiant();

    }*/
}

