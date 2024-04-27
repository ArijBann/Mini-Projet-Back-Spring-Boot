package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.EmploiDTO;
import com.springboot.MiniProject.dto.SupportCoursDTO;

public interface EmploiInterface {
    EmploiDTO getEmploiByLien(String lien , int idSup);
}
