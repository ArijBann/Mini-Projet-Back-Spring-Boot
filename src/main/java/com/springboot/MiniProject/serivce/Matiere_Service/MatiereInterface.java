package com.springboot.MiniProject.serivce.Matiere_Service;

import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;

import java.util.List;

public interface MatiereInterface {
    MatiereDTO updateMatiere (MatiereDTO mat);
    List <MatiereDTO> getAllMatiere();
}
