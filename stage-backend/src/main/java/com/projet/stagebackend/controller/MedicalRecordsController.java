package com.projet.stagebackend.controller;

import com.projet.stagebackend.entity.Consultation;
import com.projet.stagebackend.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordsController {
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    /*@GetMapping("/{idRecord}/consultations")
    public ResponseEntity<List<Consultation>> getConsultationsByMedicalRecordId(@PathVariable Integer idRecord) {
        List<Consultation> consultations = medicalRecordsService.getConsultationsByMedicalRecordId(idRecord);
        if (consultations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultations);
    }*/
}
