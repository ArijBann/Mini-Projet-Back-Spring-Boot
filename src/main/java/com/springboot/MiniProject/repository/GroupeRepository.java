package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe,Integer> {
   List<Groupe> findByFiliere(String filiere);
}
