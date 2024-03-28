package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.Consultation;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
    List<Consultation> findByPatient(User patient);

    @Query("SELECT c.patient.id FROM Consultation c WHERE c.idConsultation = :consultationId")
    Integer findUserIdByConsultationId(@Param("consultationId") Integer consultationId);
}
