package com.projet.stagebackend.service;

import com.projet.stagebackend.dto.ResponseConsultation;
import com.projet.stagebackend.dto.SelectedResponseRequest;
import com.projet.stagebackend.entity.*;
import com.projet.stagebackend.exception.ResponseNotFoundException;
import com.projet.stagebackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SelectedResponseService {

    private final SelectedResponseRepository selectedResponseRepository;
    private final UserRepository userRepository;
    private final ReponseRepository reponseRepository;
    private final QuestionRepository questionRepository;
    private final ExamenRepository examenRepository;
    private final ConsultationRepository consultationRepository;
    private final TypeExamenRepository typeExamenRepository;

    @Autowired
    public SelectedResponseService(
            SelectedResponseRepository selectedResponseRepository,
            UserRepository userRepository,
            ReponseRepository reponseRepository,
            QuestionRepository questionRepository,
            ExamenRepository examenRepository, ConsultationRepository consultationRepository,
            TypeExamenRepository typeExamenRepository) {
        this.selectedResponseRepository = selectedResponseRepository;
        this.userRepository = userRepository;
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
        this.examenRepository = examenRepository;
        this.consultationRepository = consultationRepository;
        this.typeExamenRepository = typeExamenRepository ;
    }


    public Integer addSelectedResponses(List<SelectedResponseRequest> requests) {
        Date currentDate = new Date();
        Consultation consultation = new Consultation();
        consultationRepository.save(consultation);
        for (SelectedResponseRequest request : requests) {

            // Fetch the User, Question, and Examen objects from their respective repositories
            User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Question question = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new EntityNotFoundException("Question not found"));
            Examen examen = examenRepository.findById(request.getExamenId()).orElseThrow(() -> new EntityNotFoundException("Examen not found"));

            consultation.setPatient(user);
            consultation.setDateConsultation(currentDate);
            consultationRepository.save(consultation);

            // Add the TypeExamen to the Consultation entity
            TypeExamen typeExamen = typeExamenRepository.findById(examen.getTypeExamen().getId()).orElseThrow(() -> new EntityNotFoundException("TypeExamen not found"));
            consultation.setTypeExamen(typeExamen);

            for (Long responseId : request.getResponseIds()) {
                // Fetch the Response object for each responseId
                Reponse response = reponseRepository.findById(responseId).orElseThrow(() -> new EntityNotFoundException("Response not found"));

                // Create a new SelectedResponse object
                SelectedResponse selectedResponse = new SelectedResponse();

                selectedResponse.setConsultation(consultation);

                // Check if the response type is "champ"
                if (response.getReponseType() == ReponseType.CHAMP) {
                    // Update the response text with the champResponseText from the request
                    selectedResponse.setResponse(request.getChampResponseText());
                } else {
                    selectedResponse.setResponse(response.getReponse());
                }

                selectedResponse.setUser(user);
                selectedResponse.setReponse(response);
                selectedResponse.setQuestion(question);
                selectedResponse.setExamen(examen);
                selectedResponse.setDate(currentDate);

                selectedResponseRepository.save(selectedResponse);
            }
        }
        return consultation.getIdConsultation();
    }



    public List<SelectedResponse> getSelectedResponsesByCriteria(Integer examenId, Long questionId, Integer userId) {
        return selectedResponseRepository.getSelectedResponse(examenId, questionId, userId);
    }




    public List<SelectedResponse> getSelectedResponsesForQuestionInConsultation(Long questionId, Long consultationId) {
        return selectedResponseRepository.findByQuestionIdAndConsultationId(questionId, consultationId);
    }



    public List<ResponseConsultation> getSelectedResponseDTOsForConsultation(Integer consultationId) {
        List<SelectedResponse> selectedResponses = selectedResponseRepository.findByConsultationId(consultationId);
        return selectedResponses.stream().map(this::mapToSelectedResponseDTO).collect(Collectors.toList());
    }

    private ResponseConsultation mapToSelectedResponseDTO(SelectedResponse selectedResponse) {
        ResponseConsultation dto = new ResponseConsultation();
        dto.setIdSelected(selectedResponse.getIdSelected());
        dto.setIdReponse(selectedResponse.getReponse().getIdReponse());
        dto.setDate(selectedResponse.getDate());
        dto.setResponse(selectedResponse.getResponse());
        dto.setIdQuestion(selectedResponse.getQuestion().getId());
        return dto;
    }

    public void updateSelectedResponses(Integer consultationId, Long questionId, List<Long> responseIds, String champResponseText) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found"));

        List<SelectedResponse> selectedResponsesToDelete = selectedResponseRepository.findAllByConsultationIdAndQuestionId(consultationId, questionId);

        // Get the userId and examenId from the first SelectedResponse to be deleted
        Integer userId = consultationRepository.findUserIdByConsultationId(consultationId);
        Integer examenId = questionRepository.getExamenIdByQuestionId(questionId);

        // Delete the selected responses
        selectedResponseRepository.deleteAll(selectedResponsesToDelete);

        // Fetch the User and Examen objects
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Examen examen = examenRepository.findById(examenId).orElseThrow(() -> new EntityNotFoundException("Examen not found"));

        // Fetch the Question object from the repository
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        for (Long responseId : responseIds) {
            // Fetch the Response object for each responseId
            Reponse response = reponseRepository.findById(responseId)
                    .orElseThrow(() -> new EntityNotFoundException("Response not found"));

            // Create a new SelectedResponse object
            SelectedResponse selectedResponse = new SelectedResponse();
            selectedResponse.setConsultation(consultation);
            selectedResponse.setUser(user);
            selectedResponse.setQuestion(question);
            selectedResponse.setExamen(examen);
            selectedResponse.setDate(new Date());

            // Check if the response type is "champ"
            if (response.getReponseType() == ReponseType.CHAMP) {
                selectedResponse.setReponse(response);
                selectedResponse.setResponse(champResponseText);

            } else {
                selectedResponse.setReponse(response);
                selectedResponse.setResponse(response.getReponse());
            }

            selectedResponseRepository.save(selectedResponse);
        }
    }


}
