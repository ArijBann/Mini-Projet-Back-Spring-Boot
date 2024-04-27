package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.DemandeDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;
import com.springboot.MiniProject.entity.Demande;
import com.springboot.MiniProject.entity.Matiere;
import com.springboot.MiniProject.entity.User;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.DemandeRepository;
import com.springboot.MiniProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DemandeService implements DemandeInterface {
    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    public List<DemandeDTO> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DemandeDTO> getAllDemandesByUser() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DemandeDTO getDemandeById(long id) {
        Demande demande = demandeRepository.findById(id).orElse(null);
        if (demande != null) {
            return convertToDTO(demande);
        }
        return null;
    }
    public DemandeDTO createDemande(DemandeDTO demandeDTO) {
        Demande demande = convertToEntity(demandeDTO);
        demande.setDateCreation(new Date());
        demande.setStatut("Nouvelle");
        Demande savedDemande = demandeRepository.save(demande);
        notificationService.informAdminNewDemandeCreated(convertToDTO(savedDemande));
        return convertToDTO(savedDemande);
    }

    public DemandeDTO updateDemande(Long id, DemandeDTO demandeDTO) {
        Demande existingDemande = demandeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Demande not found with id: " + id));
        // Update existing demande
        LocalDate currentDate = LocalDate.now();
        Date currentDateAsDate = java.sql.Date.valueOf(currentDate);
        if (existingDemande.getStatut().equals("Nouvelle")){
        existingDemande.setDateCreation(currentDateAsDate);
        existingDemande.setSujet(demandeDTO.getSujet());
        existingDemande.setDescription(demandeDTO.getDescription());
        //existingDemande.setStatut(demandeDTO.getStatut());
        Demande updatedDemande = demandeRepository.save(existingDemande);
        return convertToDTO(updatedDemande);
        } else{
            Demande demnull = new Demande();
            return convertToDTO(demnull);
        }
    }
    public DemandeDTO updateDemandeTraieter(Long id ,String stat) {
        Demande existingDemande = demandeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Demande not found with id: " + id));
        LocalDate currentDate = LocalDate.now();
        Date currentDateAsDate = java.sql.Date.valueOf(currentDate);
        // Update existing demande
        if (existingDemande.getStatut().equals("Nouvelle")){
        existingDemande.setStatut(stat);
        existingDemande.setDateCreation(currentDateAsDate);}
        Demande updatedDemande = demandeRepository.save(existingDemande);
        return convertToDTO(updatedDemande);
    }


    public void deleteDemande(long id) {
        demandeRepository.deleteById(id);
    }

    // Méthode pour convertir une entité Demande en DemandeDTO
    private DemandeDTO convertToDTO(Demande demande) {
        DemandeDTO demandeDTO = new DemandeDTO();
        demandeDTO.setId(demande.getId());
        demandeDTO.setSujet(demande.getSujet());
        demandeDTO.setDescription(demande.getDescription());
        demandeDTO.setDateCreation(demande.getDateCreation());
        demandeDTO.setStatut(demande.getStatut());
        User user = demande.getUser();
        if (user != null) {
            demandeDTO.setUserEmail(user.getEmail());
        }
        return demandeDTO;
    }

    // Méthode pour convertir un DemandeDTO en une entité Demande
    private Demande convertToEntity(DemandeDTO demandeDTO) {
        Demande demande = new Demande();
        Optional<User> us = userService.getusbymail(demandeDTO.getUserEmail());

        demande.setSujet(demandeDTO.getSujet());
        demande.setDescription(demandeDTO.getDescription());
        demande.setDateCreation(demandeDTO.getDateCreation());
        demande.setStatut(demandeDTO.getStatut());
        demande.setUser(us.orElse(null));
        return demande;
    }

    @Override
    public List<DemandeDTO> getDemandeByUserEmail(String email) {
        List <Demande> demandes =demandeRepository.findAll();
       // System.out.println(demandes);
        List<DemandeDTO> demandeDTOS = new ArrayList<>();
        for (Demande demande : demandes) {
            /*if (email.equals(demande.getUser().getEmail())){
            DemandeDTO demandeDTO = convertToDTO(demande);
            // Add the DTO to the list
            demandeDTOS.add(demandeDTO);}*/
            User user = demande.getUser();
            if (user != null && email.equals(user.getEmail())) {
                DemandeDTO demandeDTO = convertToDTO(demande);
                demandeDTOS.add(demandeDTO);
            }
        }
        return demandeDTOS;
     /*   List <Demande> userDem = new ArrayList<>();
        for (Demande demande : demandes) {
            if (email.equals(demande.getUser().getEmail())){
                userDem.add(demande)    ;
            }
        }
        return userDem.stream().map(this::convertToDTO).collect(Collectors.toList());
*/
    }



}

