package com.springboot.MiniProject.serivce;


import com.springboot.MiniProject.dto.*;
import com.springboot.MiniProject.entity.*;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private MatiereRepository matiereRepository ;

@Autowired
    private GroupeRepository groupeRepository;
@Autowired
private ArchiveUsersRepository archiveUsersRepository;
@Autowired
private DepartementRepository departementRepository;

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
    public Optional<User> getusbymail(String email ){
       return userRepository.findByEmail(email);
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
    public String addUserToArchive(User user , String description){
        if (user!=null) {
            ArchiveUsers archiveUsers = new ArchiveUsers();
            boolean userExists = archiveUsersRepository
                    .findByEmail(user.getEmail())
                    .isPresent();
            if (userExists) {
                throw new IllegalStateException("email already taken");
            } else {
                archiveUsers.setNom(user.getNom());
                archiveUsers.setPrenom(user.getPrenom());
                archiveUsers.setEmail(user.getEmail());
                archiveUsers.setPassword(user.getPassword());
                archiveUsers.setCIN(user.getCIN());
                archiveUsers.setDate_nais(user.getDate_nais());
                archiveUsers.setNumtel(user.getNumtel());
                archiveUsers.setEtudiant(user.getEtudiant());
                archiveUsers.setEnseignant(user.getEnseignant());
                archiveUsers.setAdmin(user.getAdmin());
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
        Enseignant user = enseignantRepository.findByNumProf(numProf);
        UserEnseigantDTO userEnseigantDTO =new UserEnseigantDTO(userRepository.findUserByEnseignantId(user.getId()), user);
        String msg = addUserToArchive(userEnseigantDTO.getUser(),description);
        userRepository.deleteById(userEnseigantDTO.getUser().getId());
        //enseignantRepository.deleteById(userEnseigantDTO.getEnseignant().getId());
        return "Enseignant Deleted Successfully from users !";
    }

    public String deleteEtud (int numInscrit, String description){
        Etudiant user = etudiantRepository.findEtudiantByNumInscri(numInscrit);
        UserEtudiantDTO userEtudiantDTO =new UserEtudiantDTO(userRepository.findUserByEtudiantId(user.getId()), user);
        String msg = addUserToArchive(userEtudiantDTO.getUser(),description);
        userRepository.deleteById(userEtudiantDTO.getUser().getId());
        return "Etudiant Deleted Successfully from users !";
    }

    public String deleteAdmin (int id,String description){
        User myUserEns = userRepository.findUserByAdminId((id));
        String msg = addUserToArchive(myUserEns,description);
        userRepository.deleteById(myUserEns.getId());
        //adminRepository.deleteById(id);
        return "Admin Deleted Successfully !";
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
        User user=userRepository.findUserByEtudiantId(etudiant.getId());
        EtudiantDTO etudiantDTO = getEtudiantDTO(etudiant, user);
        return etudiantDTO;
    }
    @Override
    public List<EtudiantDTO> findByIdGroupe(int idGroupe) {
        List<Etudiant> etudiants = etudiantRepository.findByGroupeId(idGroupe);
        List<EtudiantDTO> etudiantDTOs = new ArrayList<>();

        for (Etudiant etudiant : etudiants) {
            // Assuming that User information is available in the Etudiant entity
            User user=userRepository.findUserByEtudiantId(etudiant.getId());
            if (user!=null) {
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
            User user=userRepository.findUserByEtudiantId(etudiant.getId());
            if (user!=null) {
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
            User user=userRepository.findUserByEnseignantId(enseignant.getId());
            if (user!=null) {
                EnseignantDTO enseignantDTO = getEnsignantDTO(enseignant, user);
                // Add the DTO to the list
                enseignantsDTO.add(enseignantDTO);
            }
        }
        return enseignantsDTO;
    }
    private static EtudiantDTO getEtudiantDTO(Etudiant etudiant, User user) {
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtudiant(etudiant.getId());
        etudiantDTO.setNum_inscri(etudiant.getNumInscri());
        etudiantDTO.setIdGroupe(etudiant.getGroupe().getId());
        etudiantDTO.setCIN(user.getCIN());
        etudiantDTO.setNom(user.getNom());
        etudiantDTO.setPrenom(user.getPrenom());
        etudiantDTO.setEmail(user.getEmail());
        etudiantDTO.setNumtel(user.getNumtel());
        etudiantDTO.setPassword(user.getPassword());
        etudiantDTO.setDate_nais(user.getDate_nais());
        return etudiantDTO;
    }




    @Override
    public EnseignantDTO findByNumProf(int numProf) {
        Enseignant etudiant = enseignantRepository.findByNumProf(numProf);
        User user=userRepository.findUserByEnseignantId(etudiant.getId());
        EnseignantDTO etudiantDTO = getEnsignantDTO(etudiant, user);
        return etudiantDTO;
    }

    @Override
    public List<EnseignantDTO> findByIdDepartement(Long idDepartement) {
        Departement departement = departementRepository.findById(idDepartement)
                .orElseThrow(() -> new NotFoundException("Département non trouvé avec l'ID: " + idDepartement));

        List<Enseignant> enseignants = enseignantRepository.findByDepartement(departement);
        List<EnseignantDTO> enseignantDTOs = new ArrayList<>();
        for (Enseignant enseignant : enseignants) {
            User user = userRepository.findUserByEnseignantId(enseignant.getId());
            EnseignantDTO enseignantDTO = getEnsignantDTO(enseignant, user);
            enseignantDTOs.add(enseignantDTO);
        }

        return enseignantDTOs;

    }
    private static EnseignantDTO getEnsignantDTO (Enseignant etudiant, User user) {
        EnseignantDTO enseignantDTO = new EnseignantDTO();
        double cinnul =0;
        double telnul=0;
        enseignantDTO.setIdEnseignant(etudiant.getId());
        enseignantDTO.setNum_prof(etudiant.getNumProf());
        enseignantDTO.setDiplome(etudiant.getDiplome());
        enseignantDTO.setIdGroupes(etudiant.getGroupes().stream().map(Groupe::getId).collect(Collectors.toList()));

        if (user != null) {
        enseignantDTO.setCIN(user.getCIN());
        enseignantDTO.setNom(user.getNom());
        enseignantDTO.setPrenom(user.getPrenom());
        enseignantDTO.setEmail(user.getEmail());
        enseignantDTO.setNumtel(user.getNumtel());
        enseignantDTO.setDate_nais(user.getDate_nais());
        enseignantDTO.setPassword(user.getPassword());
    } else {
        // Traitez le cas où l'objet user est null
        // Vous pouvez définir des valeurs par défaut ou effectuer d'autres actions appropriées
        // Par exemple :

        enseignantDTO.setCIN(cinnul);
        enseignantDTO.setNom("");
        enseignantDTO.setPrenom("");
        enseignantDTO.setEmail("");
        enseignantDTO.setNumtel(telnul);
        enseignantDTO.setDate_nais(null);
        enseignantDTO.setPassword("");
    }
      /*

        enseignantDTO.setCIN(user.getCIN());
        enseignantDTO.setNom(user.getNom());
        enseignantDTO.setPrenom(user.getPrenom());
        enseignantDTO.setEmail(user.getEmail());
        enseignantDTO.setNumtel(user.getNumtel());
        enseignantDTO.setDate_nais(user.getDate_nais());
        enseignantDTO.setPassword(user.getPassword());
        */
        return enseignantDTO;
    }


    @Override
    public EnseignantDTO updateEnseignant(EnseignantDTO enseignant) {
        Enseignant enseignantExist = enseignantRepository.findByNumProf(enseignant.getNum_prof());
        User userExist = userRepository.findUserByEnseignantId(enseignantExist.getId());
        List <Groupe> groupes = groupeRepository.findAllById(enseignant.getIdGroupes());
        enseignantExist.setGroupes(groupes);
        enseignantExist.setDiplome(enseignant.getDiplome());
        userExist.setNom(enseignant.getNom());
        userExist.setEmail(enseignant.getEmail());
        userExist.setPrenom(enseignant.getPrenom());
        userExist.setNumtel(enseignant.getNumtel());
        userExist.setCIN(enseignant.getCIN());
        userExist.setDate_nais(enseignant.getDate_nais());
        userExist.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        userRepository.save(userExist);
        enseignantRepository.save(enseignantExist);
        EnseignantDTO enseignantDTOExists = getEnsignantDTO(enseignantExist, userExist);
        return enseignantDTOExists;
    }
    public EtudiantDTO updateEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiantExist = etudiantRepository.findEtudiantByNumInscri(etudiantDTO.getNum_inscri());
        User userExist = userRepository.findUserByEtudiantId(etudiantExist.getId());
        Optional<Groupe> groupe =groupeRepository.findById(etudiantExist.getId());
        etudiantExist.setGroupe(groupe.get());
        userExist.setNom(etudiantDTO.getNom());
        userExist.setEmail(etudiantDTO.getEmail());
        userExist.setPrenom(etudiantDTO.getPrenom());
        userExist.setNumtel(etudiantDTO.getNumtel());
        userExist.setCIN(etudiantDTO.getCIN());
        userExist.setDate_nais(etudiantDTO.getDate_nais());
        userExist.setPassword(passwordEncoder.encode(etudiantDTO.getPassword()));
        userRepository.save(userExist);
        etudiantRepository.save(etudiantExist);
        EtudiantDTO enseignantDTOExists = getEtudiantDTO(etudiantExist, userExist);
        return enseignantDTOExists;
    }




}
