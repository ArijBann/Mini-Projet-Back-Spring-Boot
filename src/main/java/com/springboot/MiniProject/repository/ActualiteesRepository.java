package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Actualitees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActualiteesRepository extends JpaRepository<Actualitees , Integer> {
    List <Actualitees> findByTargetAudiance(String target);
}
