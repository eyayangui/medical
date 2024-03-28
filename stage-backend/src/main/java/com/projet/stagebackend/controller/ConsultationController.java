package com.projet.stagebackend.controller;

import com.projet.stagebackend.entity.Consultation;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.TypeExamen;
import com.projet.stagebackend.entity.User;
import com.projet.stagebackend.repository.ConsultationRepository;
import com.projet.stagebackend.service.ConsultationService;
import com.projet.stagebackend.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;
    private final UserService userService;
    private final ConsultationRepository consultationRepository;

    public ConsultationController(ConsultationService consultationService,
                                  UserService userService, ConsultationRepository consultationRepository) {
        this.consultationService = consultationService;
        this.userService = userService;
        this.consultationRepository = consultationRepository;
    }

    @GetMapping("/patient/{userId}")
    public ResponseEntity<List<Consultation>> getConsultationsForPatient(@PathVariable Integer userId) {
        User patient = userService.findById(userId);
        List<Consultation> consultations = consultationService.getConsultationsForPatient(patient);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/{consultationId}/typeExamen")
    public ResponseEntity<TypeExamen> getTypeExamenForConsultation(@PathVariable Integer consultationId) {
        TypeExamen typeExamen = consultationService.getTypeExamenForConsultation(consultationId);
        return ResponseEntity.ok(typeExamen);
    }

    @PostMapping("/saveTypeExamenId")
    public ResponseEntity<String> saveTypeExamenId(@RequestParam Integer consultationId, @RequestParam Integer typeExamenId) {
        consultationService.saveTypeExamenId(consultationId, typeExamenId);
        return ResponseEntity.ok("TypeExamenId saved successfully in Consultation.");
    }

}
