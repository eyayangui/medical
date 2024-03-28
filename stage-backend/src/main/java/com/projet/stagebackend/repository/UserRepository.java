package com.projet.stagebackend.repository;

import com.projet.stagebackend.dto.UserDto;
import com.projet.stagebackend.entity.Role;
import com.projet.stagebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  List<User> findByRole(Role role);
  Optional<User> findById(Integer id);

  @Query("SELECT u FROM User u JOIN FETCH u.medicalRecords WHERE u.role = :role")
  List<User> findByRoleWithMedicalRecords(@Param("role") Role role);

}
