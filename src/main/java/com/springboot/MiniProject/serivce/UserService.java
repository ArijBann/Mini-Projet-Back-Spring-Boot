package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.EtudRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   /* @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository repository;
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system ";
    }*/

    /****************************************************/
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EtudRepository etudiantRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    public String addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user added to system ";
    }

    public String addEns(User user,Enseignant ens){
       Enseignant enseignant = enseignantRepository.save(ens);
       user.setEnseignant(enseignant);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "ens added to the system";
    }
    public String addEtud(Etudiant etud){
        etudiantRepository.save(etud);
        return "etud added to the system";
    }
}
