package com.springboot.MiniProject.serivce;


import com.springboot.MiniProject.dto.UserAdminDTO;
import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.dto.UserEtudiantDTO;
import com.springboot.MiniProject.entity.Admin;
import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.repository.AdminRepository;
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
@Autowired
private AdminRepository adminRepository;

    public String addEns(UserEnseigantDTO userEnseigantDTO){
        User user = userEnseigantDTO.getUser();
        Enseignant enseignant=userEnseigantDTO.getEnseignant();
        enseignantRepository.save(enseignant);
        user.setEnseignant(enseignant);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "enseignant added to the system";
    }
    public String addAdmin(UserAdminDTO userAdminDTO){
        User user = userAdminDTO.getUser();
        Admin admin=userAdminDTO.getAdmin();
        adminRepository.save(admin);
        user.setAdmin(admin);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "admin added to the system";
    }
    public String addEtud(UserEtudiantDTO userEtudiantDTO){
        User user = userEtudiantDTO.getUser();
        Etudiant etudiant=userEtudiantDTO.getEtudiant();
        etudiantRepository.save(etudiant);
        user.setEtudiant(etudiant);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "etudiant added to the system";
    }


}
