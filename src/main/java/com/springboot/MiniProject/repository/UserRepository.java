package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.Enseignant;
import com.springboot.MiniProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    User findUserByEnseignantId(int idEnseignant);
    User findUserByAdminId(int idAdmin);
    User findUserByEtudiantId(int idEtudiant);
    //Optional <User>  findUserByNumProf (int numProf);
    //Optional <User> findUserByRoles(String role );
}
