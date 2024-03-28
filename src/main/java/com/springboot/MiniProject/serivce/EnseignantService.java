package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.dto.EtudiantDTO;
import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.EtudRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
