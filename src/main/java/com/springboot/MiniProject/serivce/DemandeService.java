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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        existingDemande.setSujet(demandeDTO.getSujet());
        existingDemande.setDescription(demandeDTO.getDescription());
        existingDemande.setStatut(demandeDTO.getStatut());
        Demande updatedDemande = demandeRepository.save(existingDemande);
        return convertToDTO(updatedDemande);
    }
    public DemandeDTO updateDemandeTraieter(Long id ,String stat) {
        Demande existingDemande = demandeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Demande not found with id: " + id));
        // Update existing demande
        existingDemande.setStatut(stat);
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
        List<DemandeDTO> demandeDTOS = new ArrayList<>();
        for (Demande demande : demandes) {
            if (demande.getUser().getEmail() == email){
            DemandeDTO demandeDTO = convertToDTO(demande);
            // Add the DTO to the list
            demandeDTOS.add(demandeDTO);}
        }
        return demandeDTOS;
    }



}

