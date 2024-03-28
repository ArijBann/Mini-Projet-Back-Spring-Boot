package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EnseignantDTO;

import java.util.List;

public interface EnseignantInterface {
    List<EnseignantDTO>  findAllEnseignant();
    EnseignantDTO findByNumProf(double numProf);
}
