package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Etudiant;
import com.springboot.MiniProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudRepository extends JpaRepository<Etudiant,Integer> {
}
