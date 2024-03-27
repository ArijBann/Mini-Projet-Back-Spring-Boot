package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.entity.Groupe;
import com.springboot.MiniProject.repository.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupeService {
    @Autowired
    GroupeRepository groupeRepository;
    public Optional<Groupe> getGroupeById (int id ){
        return groupeRepository.findById(id);
    }
}
