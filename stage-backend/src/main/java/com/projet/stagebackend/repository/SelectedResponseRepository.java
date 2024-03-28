package com.projet.stagebackend.repository;

import com.projet.stagebackend.dto.SelectedResponseRequest;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.Reponse;
import com.projet.stagebackend.entity.SelectedResponse;
import com.projet.stagebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SelectedResponseRepository extends JpaRepository<SelectedResponse, Long> {
    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.examen.idExamen = :examenId AND sr.question.id = :questionId AND sr.user.id = :userId")
    List<SelectedResponse> getSelectedResponse(@Param("examenId") Integer examenId, @Param("questionId") Long questionId, @Param("userId") Integer userId);


    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.idSelected IN (SELECT sr2.idSelected FROM SelectedResponse sr2 WHERE sr2.question.id = :questionId) AND sr.consultation.idConsultation = :consultationId")
    List<SelectedResponse> findByQuestionIdAndConsultationId(@Param("questionId") Long questionId, @Param("consultationId") Long consultationId);

    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.consultation.idConsultation = :consultationId")
    List<SelectedResponse> findByConsultationId(@Param("consultationId") Integer consultationId);

    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.consultation.idConsultation = :consultationId AND sr.question.id = :questionId AND sr.reponse.idReponse = :responseId")
    Optional<SelectedResponse> findByConsultationIdAndQuestionIdAndReponseId(@Param("consultationId") Integer consultationId, @Param("questionId") Long questionId, @Param("responseId") Long responseId);

    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.consultation.idConsultation = ?1 AND sr.question.id = ?2 AND sr.idSelected = ?3")
    Optional<SelectedResponse> findByConsultationIdAndQuestionIdAndIdSelected(Integer consultationId, Long questionId, Long idSelected);


    @Modifying
    @Query("DELETE FROM SelectedResponse sr WHERE sr.consultation.idConsultation = :consultationId AND sr.question.id = :questionId AND sr.reponse.idReponse = :responseId")
    void deleteByConsultationIdAndQuestionIdAndReponseId(@Param("consultationId") Integer consultationId, @Param("questionId") Long questionId, @Param("responseId") Long responseId);

    @Query("SELECT sr FROM SelectedResponse sr WHERE sr.consultation.idConsultation = :consultationId AND sr.question.id = :questionId")
    List<SelectedResponse> findAllByConsultationIdAndQuestionId(@Param("consultationId") Integer consultationId, @Param("questionId") Long questionId);

}
