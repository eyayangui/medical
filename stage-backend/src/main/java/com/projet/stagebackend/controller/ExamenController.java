package com.projet.stagebackend.controller;

import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.dto.ExamenRequest;
import com.projet.stagebackend.dto.ExamenResponse;
import com.projet.stagebackend.entity.TypeExamen;
import com.projet.stagebackend.exception.ResourceNotFoundException;
import com.projet.stagebackend.repository.ExamenRepository;
import com.projet.stagebackend.repository.TypeExamenRepository;
import com.projet.stagebackend.service.ExamenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/examens")
public class ExamenController {

    @Autowired
    private final ExamenService examenService;
    private final TypeExamenRepository typeExamenRepository;
    private final ExamenRepository examenRepository;

    @Autowired
    public ExamenController(ExamenService examenService,
                            TypeExamenRepository typeExamenRepository,
                            ExamenRepository examenRepository) {
        this.examenService = examenService;
        this.typeExamenRepository = typeExamenRepository;
        this.examenRepository = examenRepository;
    }



    @PostMapping("/addexamen")
    public ResponseEntity<Examen> addExamen(@Valid @RequestBody ExamenRequest examenRequest) {
        Examen newExamen = examenService.addExamen(examenRequest);
        return ResponseEntity.ok(newExamen);
    }

    @GetMapping("/allexamens")
    public ResponseEntity<List<ExamenResponse>> getAllExamens() {
        List<ExamenResponse> examenResponses = examenService.getAllExamensWithTypeName();
        return ResponseEntity.ok(examenResponses);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExamen(@PathVariable int id) {
        examenService.deleteExamen(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Examen deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addTypeExamen")
    public ResponseEntity<TypeExamen> addTypeExamen(@RequestBody TypeExamen typeExamen) {
        TypeExamen addedTypeExamen = examenService.addTypeExamen(typeExamen);
        return new ResponseEntity<>(addedTypeExamen, HttpStatus.CREATED);
    }


    @GetMapping("/alltypeexamens")
    public List<TypeExamen> getAllTypeExamens() {
        return typeExamenRepository.findAll();
    }

    @GetMapping("/byType/{typeExamenId}")
    public ResponseEntity<List<Examen>> getExamsByType(@PathVariable Integer typeExamenId) {
        TypeExamen typeExamen = new TypeExamen();
        typeExamen.setId(typeExamenId);

        List<Examen> exams = examenService.getExamsByType(typeExamen);

        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(exams);
        }
    }

    @GetMapping("/byTypeExamen/{typeExamenId}")
    public ResponseEntity<Integer> getExamIdByTypeExamen(@PathVariable Integer typeExamenId) {
        Integer examId = examenService.getExamIdByTypeExamen(typeExamenId);

        if (examId != null) {
            return ResponseEntity.ok(examId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/for-consultation/{consultationId}")
    public ResponseEntity<List<Examen>> getExamsForConsultation(@PathVariable Long consultationId) {
        List<Examen> exams = examenService.getExamsForConsultation(consultationId);
        return ResponseEntity.ok(exams);
    }


}
