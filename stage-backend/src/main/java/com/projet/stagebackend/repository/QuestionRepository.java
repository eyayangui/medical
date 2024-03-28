package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.Question;
import com.projet.stagebackend.entity.ReponseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExamenIdExamen(Integer exam_id);

    @Query("SELECT q FROM Question q " +
            "JOIN q.reponses r " +
            "WHERE q.label = :label " +
            "AND r.reponse = :reponse " +
            "AND r.reponseType = :reponseType " +
            "AND q.examen.idExamen = :examId")
    Question findQuestionByLabelAndReponseAndReponseTypeAndExamen(
            @Param("label") String label,
            @Param("reponse") String reponse,
            @Param("reponseType") ReponseType reponseType,
            @Param("examId") Long examId
    );

    List<Question> findByExamen(Examen examen);


    /*@Query("SELECT q FROM Question q WHERE q.examen.idExamen = :examId")
    List<Question> findQuestionsByExamId(@Param("examId") Integer examId);*/

    @Query("SELECT DISTINCT q " +
            "FROM Question q " +
            "INNER JOIN SelectedResponse sr " +
            "ON q.id = sr.question.id " +
            "WHERE q.examen.idExamen = :examId " +
            "AND sr.consultation.idConsultation = :consultationId")
    List<Question> findByExamIdAndConsultationId(@Param("examId") Long examId, @Param("consultationId") Long consultationId);

    @Query("SELECT q.examen.idExamen FROM Question q WHERE q.id = :questionId")
    Integer getExamenIdByQuestionId(@Param("questionId") Long questionId);

}
