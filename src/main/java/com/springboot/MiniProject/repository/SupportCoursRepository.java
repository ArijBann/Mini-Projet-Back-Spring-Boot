package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.SupportCours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportCoursRepository  extends JpaRepository<SupportCours, Integer> {
    List<SupportCours> findByMatiere_Id(int id);
}
