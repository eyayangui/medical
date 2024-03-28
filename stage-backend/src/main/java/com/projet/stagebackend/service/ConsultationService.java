package com.projet.stagebackend.service;

import com.projet.stagebackend.entity.Consultation;
import com.projet.stagebackend.entity.Examen;
import com.projet.stagebackend.entity.TypeExamen;
import com.projet.stagebackend.entity.User;
import com.projet.stagebackend.repository.ConsultationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;


    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public List<Consultation> getConsultationsForPatient(User patient) {
        return consultationRepository.findByPatient(patient);
    }

    public TypeExamen getTypeExamenForConsultation(Integer consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found for id: " + consultationId));
        return consultation.getTypeExamen();
    }

    public void saveTypeExamenId(Integer consultationId, Integer typeExamenId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found"));
        TypeExamen typeExamen = new TypeExamen();
        typeExamen.setId(typeExamenId);
        consultation.setTypeExamen(typeExamen);
        consultationRepository.save(consultation);
    }

}
