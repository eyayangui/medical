package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.Reponse;
import com.projet.stagebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReponseRepository extends JpaRepository<Reponse,Long> {

    @Query("SELECT r FROM Reponse r WHERE r.idReponse = :responseId")
    Optional<Reponse> findById(@Param("responseId") List<Long> responseIds);


}
