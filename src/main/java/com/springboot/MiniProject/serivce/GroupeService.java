package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.exception.NotFoundException;
import com.springboot.MiniProject.repository.EnseignantRepository;
import com.springboot.MiniProject.repository.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class GroupeService {
    @Autowired
    GroupeRepository groupeRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    public Optional<Groupe> getGroupeById (int id ){
        return groupeRepository.findById(id);
    }

    public void assignerEnseignantAuGroupe(int idEnseignant, int idGroupe) {
        Enseignant enseignant = enseignantRepository.findById(idEnseignant)
                .orElseThrow(() -> new NotFoundException("Enseignant not found with id: " + idEnseignant));

        Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe not found with id: " + idGroupe));

        if (enseignant.getGroupes() == null) {
            enseignant.setGroupes(new ArrayList<>());
        }

        enseignant.getGroupes().add(groupe);
        enseignantRepository.save(enseignant);
    }
}