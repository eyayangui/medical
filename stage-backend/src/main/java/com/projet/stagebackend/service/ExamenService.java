package com.projet.stagebackend.service;

import com.projet.stagebackend.dto.ExamQuestionDTO;
import com.projet.stagebackend.dto.ExamenRequest;
import com.projet.stagebackend.dto.ExamenResponse;
import com.projet.stagebackend.entity.TypeExamen;
import com.projet.stagebackend.mapper.UserMapperImpl;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.repository.ExamenRepository;
import com.projet.stagebackend.repository.TypeExamenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamenService {

    private UserMapperImpl dtoMapper;

    @Autowired
    private final ExamenRepository examenRepository;
    private final TypeExamenRepository typeExamenRepository;



    @Autowired
    public ExamenService(ExamenRepository examenRepository,
                         TypeExamenRepository typeExamenRepository) {
        this.examenRepository = examenRepository;
        this.typeExamenRepository = typeExamenRepository;
    }


    public void deleteExamen(Integer id) {
        examenRepository.deleteById(id);
    }


    public Examen addExamen(ExamenRequest examenRequest) {
        String typeExamenName = examenRequest.getTypeExamen();

        // Find the TypeExamen entity based on its name
        TypeExamen typeExamen = typeExamenRepository.findByName(typeExamenName);

        /*if (typeExamen == null) {
            // Handle the case where the specified typeExamen doesn't exist
            throw new InvalidTypeExamenException("Invalid typeExamen");
        }
*/
        Examen newExamen = new Examen();
        newExamen.setLibelle(examenRequest.getLibelle());
        newExamen.setTypeExamen(typeExamen);

        return examenRepository.save(newExamen);
    }




    public List<ExamenResponse> getAllExamensWithTypeName() {
        List<Examen> examens = examenRepository.findAll();

        return examens.stream()
                .map(this::mapToExamenResponse)
                .collect(Collectors.toList());
    }

    private ExamenResponse mapToExamenResponse(Examen examen) {
        ExamenResponse response = new ExamenResponse();
        response.setIdExamen(examen.getIdExamen());
        response.setLibelle(examen.getLibelle());
        if (examen.getTypeExamen() != null) {
            response.setTypeExamen(examen.getTypeExamen().getName());
            response.setTypeExamenName(examen.getTypeExamen().getName());
            response.setTypeExamenId(examen.getTypeExamen().getId());
        }

        return response;
    }



    public Examen getExamById(Integer examId) {
        return examenRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with ID: " + examId));
    }

    public TypeExamen addTypeExamen(TypeExamen typeExamen) {
        return typeExamenRepository.save(typeExamen);
    }



    public List<Examen> getExamsByType(TypeExamen typeExamen) {
        return examenRepository.findByTypeExamen(typeExamen);
    }

    public Integer getExamIdByTypeExamen(Integer typeExamenId) {
        Optional<TypeExamen> typeExamenOptional = typeExamenRepository.findById(typeExamenId);

        if (typeExamenOptional.isPresent()) {
            TypeExamen typeExamen = typeExamenOptional.get();

            // Find the exam associated with the given TypeExamen
            Examen exam = examenRepository.findFirstByTypeExamen(typeExamen);

            if (exam != null) {
                return exam.getIdExamen();
            }
        }
        return null; // TypeExamen not found or no associated exam
    }

    public List<Examen> getExamsForConsultation(Long consultationId) {
        // Implement logic to retrieve exams based on the consultation ID
        return examenRepository.findByConsultationId(consultationId);
    }

}
