package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement,Long> {
    Departement findByNom(String nom);
}
