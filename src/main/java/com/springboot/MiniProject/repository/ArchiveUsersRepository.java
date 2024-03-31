package com.springboot.MiniProject.repository;

import com.springboot.MiniProject.entity.ArchiveUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ArchiveUsersRepository extends JpaRepository<ArchiveUsers,Integer> {
    Optional<ArchiveUsers> findByEmail(String email);
    }


