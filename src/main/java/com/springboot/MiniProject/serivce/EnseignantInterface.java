package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EnseignantDTO;
import com.springboot.MiniProject.entity.Enseignant;

import java.util.List;

public interface EnseignantInterface {
    List<EnseignantDTO>  findAllEnseignant();
    EnseignantDTO findByNumProf(int numProf);
    EnseignantDTO updateEnseignant(EnseignantDTO enseignantDTO);
}
