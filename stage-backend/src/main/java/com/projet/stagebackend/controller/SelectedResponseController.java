package com.projet.stagebackend.controller;

import com.projet.stagebackend.dto.ResponseConsultation;
import com.projet.stagebackend.dto.SelectedResponseRequest;
import com.projet.stagebackend.dto.UpdateRequest;
import com.projet.stagebackend.entity.SelectedResponse;
import com.projet.stagebackend.entity.TypeExamen;
import com.projet.stagebackend.service.SelectedResponseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/selected-responses")
public class SelectedResponseController {

    private final SelectedResponseService selectedResponseService;

    @Autowired
    public SelectedResponseController(SelectedResponseService selectedResponseService) {
        this.selectedResponseService = selectedResponseService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addSelectedResponses(@RequestBody List<SelectedResponseRequest> requests) {
        Integer newConsultationId = selectedResponseService.addSelectedResponses(requests);
        // Return the ID in the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Selected responses added successfully.");
        response.put("consultationId", newConsultationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/responses")
    public ResponseEntity<List<SelectedResponse>> getSelectedResponsesByCriteria(
            @RequestParam Integer examenId,
            @RequestParam Long questionId,
            @RequestParam Integer userId) {

        List<SelectedResponse> selectedResponses = selectedResponseService.getSelectedResponsesByCriteria(examenId, questionId, userId);

        if (selectedResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(selectedResponses);
        }
    }

    @GetMapping("/for-question/{questionId}/{consultationId}")
    public ResponseEntity<List<SelectedResponse>> getSelectedResponsesForQuestionInConsultation(
            @PathVariable Long questionId,
            @PathVariable Long consultationId) {
        List<SelectedResponse> responses = selectedResponseService.getSelectedResponsesForQuestionInConsultation(questionId, consultationId);
        return ResponseEntity.ok(responses);
    }



    @GetMapping("/{consultationId}/selected-responses")
    public ResponseEntity<List<ResponseConsultation>> getSelectedResponseDTOsForConsultation(@PathVariable Integer consultationId) {
        List<ResponseConsultation> selectedResponseDTOs = selectedResponseService.getSelectedResponseDTOsForConsultation(consultationId);
        if (selectedResponseDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(selectedResponseDTOs);
    }




    @PutMapping("/{consultationId}/{questionId}")
    public ResponseEntity<String> updateSelectedResponses(
            @PathVariable Integer consultationId,
            @PathVariable Long questionId,
            @RequestBody UpdateRequest selectedResponseRequest
    ) {
        try {
            selectedResponseService.updateSelectedResponses(
                    consultationId,
                    questionId,
                    selectedResponseRequest.getResponseIds(),
                    selectedResponseRequest.getChampResponseText()
            );
            return new ResponseEntity<>("Selected responses updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating selected responses", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
