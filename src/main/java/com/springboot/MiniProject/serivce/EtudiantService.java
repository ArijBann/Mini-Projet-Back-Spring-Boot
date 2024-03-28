package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.EtudRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EtudiantService {
    @Autowired
    EtudRepository etudiantRepository;
    @Autowired
    UserRepository userRepository;
    public EtudiantDTO updateEtudiantInfo(EtudiantDTO etudiantDTO){
        User userExist = userRepository.findUserByEtudiantId(etudiantDTO.getIdEtudiant());
        userExist.setNumtel(etudiantDTO.getNumtel());
        userRepository.save(userExist);
        return etudiantDTO;
    }
}
