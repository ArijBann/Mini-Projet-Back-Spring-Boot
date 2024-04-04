package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.DemandeDTO;
import com.springboot.MiniProject.dto.MatiereDTO.MatiereDTO;

import java.util.List;

public interface DemandeInterface {
    List<DemandeDTO> getDemandeByUserEmail(String email);
}
