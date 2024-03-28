package com.springboot.MiniProject.serivce;


import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserService implements  EtudiantInterface ,EnseignantInterface{


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
        int RandomInscrit= ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
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
            userEnseigantDTO.getEnseignant().setNumProf(RandomInscrit);
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
        int RandomInscrit= ThreadLocalRandom.current().nextInt(100_000, 1_000_000);

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
            userEtudiantDTO.getEtudiant().setNumInscri(RandomInscrit);
            etudiantRepository.save(etudiant);
            user.setEtudiant(etudiant);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "etudiant added to the system";
        }


    }

    public String deleteEns (int id){
        Optional<User> myUserEns = userRepository.findUserByEnseignantId((id));
         userRepository.deleteById(myUserEns.get().getId());
         enseignantRepository.deleteById(id);
        return "Enseignant Deleted Successfully !";
    }

    public String deleteEtud (int id){
        Optional<User> myUserEns = userRepository.findUserByEtudiantId((id));
        userRepository.deleteById(myUserEns.get().getId());
        etudiantRepository.deleteById(id);
        return "Etudiant Deleted Successfully !";
    }

    public String deleteAdmin (int id){
        Optional<User> myUserEns = userRepository.findUserByAdminId((id));
        userRepository.deleteById(myUserEns.get().getId());
        adminRepository.deleteById(id);
        return "Admin Deleted Successfully !";
    }
    public User updateUser(User user){
        User existingEUser = userRepository.findById(user.getId()).orElse(null);
        existingEUser.setPassword(user.getPassword());
        existingEUser.setCIN(user.getCIN());
        existingEUser.setDate_nais(user.getDate_nais());
        existingEUser.setEmail(user.getEmail());
        existingEUser.setNom(user.getNom());
        existingEUser.setPrenom(user.getPrenom());
        existingEUser.setNumtel(user.getNumtel());
        return userRepository.save(existingEUser);
    }



   /* public UserEnseigantDTO getEnseignantByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getEnseignant() != null) {
            Enseignant enseignant = user.get().getEnseignant();
            return new UserEnseigantDTO(user.get(), enseignant);
        } else {
            return null;
        }
    }*/




    @Override
    public EtudiantDTO findByNumInscri(double numInscri) {
        Etudiant etudiant = etudiantRepository.findEtudiantByNumInscri(numInscri);
        Optional<User> user=userRepository.findUserByEtudiantId(etudiant.getId());
        EtudiantDTO etudiantDTO = getEtudiantDTO(etudiant, user);
        return etudiantDTO;
    }
    @Override
    public List<EtudiantDTO> findByIdGroupe(int idGroupe) {
        List<Etudiant> etudiants = etudiantRepository.findByGroupeId(idGroupe);
        List<EtudiantDTO> etudiantDTOs = new ArrayList<>();

        for (Etudiant etudiant : etudiants) {
            // Assuming that User information is available in the Etudiant entity
            Optional<User> user=userRepository.findUserByEtudiantId(etudiant.getId());
            if (user.isPresent()) {
                EtudiantDTO etudiantDTO = getEtudiantDTO(etudiant, user);
                // Add the DTO to the list
                etudiantDTOs.add(etudiantDTO);
            }
        }
        return etudiantDTOs;
    }

    @Override
    public List<EtudiantDTO> findAllEtudiant() {
        List <Etudiant> etudiants = etudiantRepository.findAll();
        List<EtudiantDTO> etudiantDTOs = new ArrayList<>();
        for (Etudiant etudiant : etudiants) {
            // Assuming that User information is available in the Etudiant entity
            Optional<User> user=userRepository.findUserByEtudiantId(etudiant.getId());
            if (user.isPresent()) {
                EtudiantDTO etudiantDTO = getEtudiantDTO(etudiant, user);
                // Add the DTO to the list
                etudiantDTOs.add(etudiantDTO);
            }
        }
        return etudiantDTOs;
    }
    @Override
    public List<EnseignantDTO> findAllEnseignant() {
        List <Enseignant> enseignants = enseignantRepository.findAll();
        List<EnseignantDTO> enseignantsDTO = new ArrayList<>();
        for (Enseignant enseignant : enseignants) {
            // Assuming that User information is available in the Etudiant entity
            Optional<User> user=userRepository.findUserByEnseignantId(enseignant.getId());
            if (user.isPresent()) {
                EnseignantDTO enseignantDTO = getEnsignantDTO(enseignant, user);
                // Add the DTO to the list
                enseignantsDTO.add(enseignantDTO);
            }
        }
        return enseignantsDTO;
    }
    private static EtudiantDTO getEtudiantDTO(Etudiant etudiant, Optional<User> user) {
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(etudiant.getId());
        etudiantDTO.setNum_inscri(etudiant.getNumInscri());
        etudiantDTO.setIdGroupe(etudiant.getGroupe().getId());
        etudiantDTO.setCIN(user.get().getCIN());
        etudiantDTO.setNom(user.get().getNom());
        etudiantDTO.setPrenom(user.get().getPrenom());
        etudiantDTO.setEmail(user.get().getEmail());
        etudiantDTO.setNumtel(user.get().getNumtel());
        etudiantDTO.setDate_nais(user.get().getDate_nais());
        return etudiantDTO;
    }




    @Override
    public EnseignantDTO findByNumProf(double numProf) {
        Enseignant etudiant = enseignantRepository.findByNumProf(numProf);
        Optional<User> user=userRepository.findUserByEnseignantId(etudiant.getId());
        EnseignantDTO etudiantDTO = getEnsignantDTO(etudiant, user);
        return etudiantDTO;
    }

    private static EnseignantDTO getEnsignantDTO(Enseignant etudiant, Optional<User> user) {
        EnseignantDTO etudiantDTO = new EnseignantDTO();
        etudiantDTO.setIdEnseignant(etudiant.getId());
        etudiantDTO.setNum_prof(etudiant.getNumProf());
        etudiantDTO.setIdGroupes(etudiant.getGroupes().stream().map(Groupe::getId).collect(Collectors.toList()));
        etudiantDTO.setCIN(user.get().getCIN());
        etudiantDTO.setNom(user.get().getNom());
        etudiantDTO.setPrenom(user.get().getPrenom());
        etudiantDTO.setEmail(user.get().getEmail());
        etudiantDTO.setNumtel(user.get().getNumtel());
        etudiantDTO.setDate_nais(user.get().getDate_nais());
        return etudiantDTO;
    }
}
