package com.projet.stagebackend.service;

import com.projet.stagebackend.dto.ExamQuestionDTO;
import com.projet.stagebackend.dto.QuestionDTO;
import com.projet.stagebackend.dto.QuestionRequestDto;
import com.projet.stagebackend.entity.*;
import com.projet.stagebackend.repository.ExamenRepository;
import com.projet.stagebackend.repository.QuestionRepository;
import com.projet.stagebackend.repository.TypeExamenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {


    private final ExamenRepository examRepository;
    private final QuestionRepository questionRepository;
    private final TypeExamenRepository typeExamenRepository;

    @Autowired
    public QuestionService(
            ExamenRepository examRepository,
            QuestionRepository questionRepository,
            TypeExamenRepository typeExamenRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.typeExamenRepository = typeExamenRepository;
    }



    public void createQuestionForExam(Integer examId, String questionLabel) {
        Examen exam = examRepository.findById(examId).orElse(null);

        if (exam == null) {
            // Handle case when exam doesn't exist
            return;
        }

        Question newQuestion = new Question();
        newQuestion.setLabel(questionLabel);
        newQuestion.setExamen(exam);

        questionRepository.save(newQuestion);
    }



    public List<QuestionDTO> getAllQuestionsForExam(Integer examId) {
        List<Question> questions = questionRepository.findByExamenIdExamen(examId);
        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setLabel(question.getLabel());
        return dto;
    }

    public Question getQuestionByLabelAndReponseAndReponseTypeAndExamen(
            String label, String reponse, ReponseType reponseType, Long examId) {
        return questionRepository.findQuestionByLabelAndReponseAndReponseTypeAndExamen(
                label, reponse, reponseType, examId);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }


    private QuestionRequestDto mapQuestionToDto(Question question) {
        QuestionRequestDto dto = new QuestionRequestDto();
        dto.setLabel(question.getLabel());
        dto.setId(question.getId());

        // Collect all idReponse values into a list
        dto.setIdReponses(
                question.getReponses()
                        .stream()
                        .map(Reponse::getIdReponse)
                        .collect(Collectors.toList())
        );

        dto.setResponses(
                question.getReponses()
                        .stream()
                        .map(Reponse::getReponse)
                        .collect(Collectors.toList())
        );
        dto.setResponseType(question.getReponses().isEmpty() ? null : question.getReponses().get(0).getReponseType());

        return dto;
    }

    public List<QuestionRequestDto> getQuestionRequestDtosByExam(Examen exam) {
        List<Question> questions = questionRepository.findByExamen(exam);

        return questions.stream()
                .map(this::mapQuestionToDto)
                .collect(Collectors.toList());
    }

    public ExamQuestionDTO getQuestionsByExamAndTypeExamen(Integer typeExamenId, Integer examId) {
        Optional<TypeExamen> typeExamenOptional = typeExamenRepository.findById(typeExamenId);
        Optional<Examen> examenOptional = examRepository.findById(examId);

        if (typeExamenOptional.isPresent() && examenOptional.isPresent()) {
            TypeExamen typeExamen = typeExamenOptional.get();
            Examen examen = examenOptional.get();

            // Check if the exam belongs to the specified typeExamen
            if (examen.getTypeExamen().equals(typeExamen)) {
                ExamQuestionDTO examQuestionDTO = new ExamQuestionDTO();
                examQuestionDTO.setExamen(examen);
                examQuestionDTO.setQuestions(examen.getQuestions());
                return examQuestionDTO;
            }
        }
        return null; // TypeExamen or Exam not found or mismatch
    }

    /*public List<Question> getQuestionsForExam(Integer examId) {
        // Implement logic to retrieve questions based on the exam ID
        return questionRepository.findQuestionsByExamId(examId);
    }*/

    public List<Question> getQuestionsForExamInConsultation(Long examId, Long consultationId) {
        // Implement logic to retrieve questions based on the exam ID and consultation ID
        return questionRepository.findByExamIdAndConsultationId(examId, consultationId);
    }

}
