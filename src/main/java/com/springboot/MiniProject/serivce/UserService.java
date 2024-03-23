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
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

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
    @Autowired
    private EmailSenderService emailSenderService;

    public String addEns(UserEnseigantDTO userEnseigantDTO){
        User user = userEnseigantDTO.getUser();
        Enseignant enseignant=userEnseigantDTO.getEnseignant();
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }else {
            this.emailSenderService.sendEmail(user.getEmail(),"Votre compte sur issatso+","Bonjour,\n" +
                    "Votre mot de passe sur https://issatso.rnu.tn/issatsoplus/student est : " + user.getPassword() +
                    "\n" +
                    "Pour toute question veuillez nous contacter au 71 834 746 ou par email sur iissatso@mesrs.tn");
            enseignantRepository.save(enseignant);
            user.setEnseignant(enseignant);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "enseignant added to the system";
        }

    }

    public String addAdmin(UserAdminDTO userAdminDTO){
        User user = userAdminDTO.getUser();
        Admin admin=userAdminDTO.getAdmin();
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }else {
            this.emailSenderService.sendEmail(user.getEmail(),"Votre compte sur issatso+","Bonjour,\n" +
                    "Votre mot de passe sur https://issatso.rnu.tn/issatsoplus/student est : " + user.getPassword() +
                    "\n" +
                    "Pour toute question veuillez nous contacter au 71 834 746 ou par email sur iissatso@mesrs.tn");
            adminRepository.save(admin);
            user.setAdmin(admin);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "admin added to the system";
        }

    }
    public String addEtud(UserEtudiantDTO userEtudiantDTO){
        User user = userEtudiantDTO.getUser();
        Etudiant etudiant=userEtudiantDTO.getEtudiant();

        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }else
        {

            this.emailSenderService.sendEmail(user.getEmail(),"Votre compte sur issatso+","Bonjour,\n" +
                    "Votre mot de passe sur https://issatso.rnu.tn/issatsoplus/student est :\n" +
                    "Mot de passe : " + user.getPassword() +
                    "\n" +

                    "Pour toute question veuillez nous contacter au 71 834 746 ou par email sur iissatso@mesrs.tn");

            etudiantRepository.save(etudiant);
            user.setEtudiant(etudiant);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "etudiant added to the system";
        }


    }

    public String deleteEns (int id){
        Enseignant exeistEnsegnant = enseignantRepository.findById(id).orElse(null);
        int enseignantId = exeistEnsegnant.getId();
        userRepository.deleteById(id);
        enseignantRepository.deleteById(enseignantId);

        return "Enseignant Deleted Successfully !";
    }
}
