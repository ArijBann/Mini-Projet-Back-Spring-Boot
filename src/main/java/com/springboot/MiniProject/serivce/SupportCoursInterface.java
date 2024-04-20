package com.springboot.MiniProject.serivce;

import com.springboot.MiniProject.dto.DemandeDTO;
import com.springboot.MiniProject.dto.SupportCoursDTO;

import java.util.List;

public interface SupportCoursInterface {
    SupportCoursDTO getSupportCoursByLien(String lien , int idSup);
}
