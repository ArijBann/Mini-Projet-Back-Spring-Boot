package com.springboot.MiniProject.serivce;


import com.springboot.MiniProject.dto.UserAdminDTO;
import com.springboot.MiniProject.dto.UserEnseigantDTO;
import com.springboot.MiniProject.dto.UserEtudiantDTO;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
    @Autowired
    private ArchiveUsersRepository archiveUsersRepository ;

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
            userEtudiantDTO.getEtudiant().setNum_inscri(RandomInscrit);
            etudiantRepository.save(etudiant);
            user.setEtudiant(etudiant);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "etudiant added to the system";
        }


    }

   /* public String deleteEns (int id){
        Optional<User> myUserEns = userRepository.findUserByEnseignantId((id));
         userRepository.deleteById(myUserEns.get().getId());
         enseignantRepository.deleteById(id);
        return "Enseignant Deleted Successfully !";
    }*/


    public String addUserToArchive(Optional<User> user , String description){
        if (user.isPresent()) {
            ArchiveUsers archiveUsers = new ArchiveUsers();
            boolean userExists = archiveUsersRepository
                    .findByEmail(user.get().getEmail())
                    .isPresent();
            if (userExists) {
                throw new IllegalStateException("email already taken");
            } else {
                archiveUsers.setNom(user.get().getNom());
                archiveUsers.setPrenom(user.get().getPrenom());
                archiveUsers.setEmail(user.get().getEmail());
                archiveUsers.setPassword(user.get().getPassword());
                archiveUsers.setCIN(user.get().getCIN());
                archiveUsers.setDate_nais(user.get().getDate_nais());
                archiveUsers.setNumtel(user.get().getNumtel());
                archiveUsers.setEtudiant(user.get().getEtudiant());
                archiveUsers.setEnseignant(user.get().getEnseignant());
                archiveUsers.setAdmin(user.get().getAdmin());
                archiveUsers.setDate_suppression(LocalDateTime.now());
                archiveUsers.setDescription(description);
                archiveUsersRepository.save(archiveUsers);
                return "user added to archive ";
            }
        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    public String deleteEns (int numProf,String description){
        Optional<Enseignant> user = enseignantRepository.findByNumProf(numProf);
        UserEnseigantDTO userEnseigantDTO =new UserEnseigantDTO(userRepository.findUserByEnseignantId(user.get().getId()).orElse(null), user.get());
        String msg = addUserToArchive(Optional.ofNullable(userEnseigantDTO.getUser()),description);
        userRepository.deleteById(userEnseigantDTO.getUser().getId());
        //enseignantRepository.deleteById(userEnseigantDTO.getEnseignant().getId());
        return "Enseignant Deleted Successfully from users !";
    }
    public String deleteEtud (int numInscrit, String description){
        Optional<Etudiant> user = etudiantRepository.findEtudiantByNum_inscri(numInscrit);
        UserEtudiantDTO userEtudiantDTO =new UserEtudiantDTO(userRepository.findUserByEtudiantId(user.get().getId()).orElse(null), user.get());
        String msg = addUserToArchive(Optional.ofNullable(userEtudiantDTO.getUser()),description);
        userRepository.deleteById(userEtudiantDTO.getUser().getId());
        return "Etudiant Deleted Successfully from users !";
    }

    public String deleteAdmin (int id,String description){
        Optional<User> myUserEns = userRepository.findUserByAdminId((id));
        String msg = addUserToArchive(myUserEns,description);
        userRepository.deleteById(myUserEns.get().getId());
        //adminRepository.deleteById(id);
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
    }


    public UserEnseigantDTO getEnseignantByNumProf(int numProf) {
        Optional<Enseignant> user = enseignantRepository.findByNumProf(numProf);
        if (user != null) {
            return new UserEnseigantDTO(userRepository.findUserByEnseignantId(user.get().getId()).orElse(null), user.get());
        } else {
            return null;
        }
    }

    public List<ArchiveUsers> getAllArchiveUsers() {
        return archiveUsersRepository.findAll();

    }

}
