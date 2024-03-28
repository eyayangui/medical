package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.TypeExamen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeExamenRepository extends JpaRepository<TypeExamen, Integer> {
    TypeExamen findByName(String name);
    Optional<TypeExamen> findById(Integer id);

    TypeExamen findById(int id);


}
