package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.TypeExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen,Integer> {

    List<Examen> findByTypeExamen(TypeExamen typeExamen);
    Examen findFirstByTypeExamen(TypeExamen typeExamen);

    @Query("SELECT e FROM Examen e " +
            "WHERE e.idExamen IN (SELECT sr.examen.idExamen FROM SelectedResponse sr WHERE sr.consultation.idConsultation = :consultationId)")
    List<Examen> findByConsultationId(@Param("consultationId") Long consultationId);
}
