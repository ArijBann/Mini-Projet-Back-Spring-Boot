package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import com.springboot.MiniProject.entity.SupportCours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportCoursRepository  extends JpaRepository<SupportCours, Integer> {
}