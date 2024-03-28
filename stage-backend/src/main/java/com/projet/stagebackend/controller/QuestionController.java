package com.projet.stagebackend.controller;

import com.projet.stagebackend.dto.ExamQuestionDTO;
import com.projet.stagebackend.dto.QuestionDTO;
import com.projet.stagebackend.dto.QuestionRequestDto;
import com.projet.stagebackend.entity.*;
import com.projet.stagebackend.repository.ExamenRepository;
import com.projet.stagebackend.service.ExamenService;
import com.projet.stagebackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final ExamenService examenService ;
    private final ExamenRepository examRepository;

    @Autowired
    public QuestionController(QuestionService questionService,
                              ExamenRepository examRepository,
                              ExamenService examenService) {
        this.questionService = questionService;
        this.examRepository = examRepository;
        this.examenService = examenService;
    }



    @PostMapping("/{examId}/add")
    public ResponseEntity<?> addQuestionToExam(@PathVariable Integer examId, @RequestBody QuestionRequestDto questionRequest) {
        try {
            // Create a new Question entity
            Question question = new Question();
            question.setLabel(questionRequest.getLabel());

            // Create a list of response entities
            List<Reponse> responses = new ArrayList<>();
            for (String responseText : questionRequest.getResponses()) {
                Reponse response = new Reponse();
                response.setReponse(responseText);
                response.setReponseType(questionRequest.getResponseType());
                response.setQuestion(question);
                responses.add(response);
            }

            question.setReponses(responses);

            // Retrieve the Exam entity
            Examen exam = examenService.getExamById(examId); // Replace with your actual Exam retrieval logic

            // Associate the question with the exam
            question.setExamen(exam);

            // Save the question and its responses
            questionService.saveQuestion(question);

            return ResponseEntity.ok(Map.of("message", "Question added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding question to exam");
        }
    }



    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<QuestionRequestDto>> getQuestionsByExamId(@PathVariable Integer examId) {
        try {
            Examen exam = examenService.getExamById(examId);

            if (exam == null) {
                return ResponseEntity.notFound().build();
            }
            List<QuestionRequestDto> questionRequestDtos = questionService.getQuestionRequestDtosByExam(exam);

            return ResponseEntity.ok(questionRequestDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/byType/{typeExamenId}/{examId}")
    public ResponseEntity<ExamQuestionDTO> getQuestionsByExamAndTypeExamen(
            @PathVariable Integer typeExamenId,
            @PathVariable Integer examId) {

        ExamQuestionDTO examQuestionDTO = questionService.getQuestionsByExamAndTypeExamen(typeExamenId, examId);

        if (examQuestionDTO != null) {
            return ResponseEntity.ok(examQuestionDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/for-exam/{examId}/{consultationId}")
    public ResponseEntity<List<Question>> getQuestionsForExamInConsultation(
            @PathVariable Long examId,
            @PathVariable Long consultationId) {
        List<Question> questions = questionService.getQuestionsForExamInConsultation(examId, consultationId);
        return ResponseEntity.ok(questions);
    }

}
