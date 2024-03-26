package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnseignantService {
   /* @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private  UserRepository userRepository;
    public List<UserEnseigantDTO> getAllEnseignants() {
        List<Enseignant> enseignants = enseignantRepository.findAll();
        return enseignants.stream()
                .map(enseignant -> new UserEnseigantDTO(userRepository.findUserByEnseignantId(enseignant.getId()).orElse(null), enseignant))
                .collect(Collectors.toList());
    }

    public UserEnseigantDTO getEnseignantByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getEnseignant() != null) {
            Enseignant enseignant = user.get().getEnseignant();
            return new UserEnseigantDTO(user.get(), enseignant);
        } else {
            return null;
        }
    }*/

}
