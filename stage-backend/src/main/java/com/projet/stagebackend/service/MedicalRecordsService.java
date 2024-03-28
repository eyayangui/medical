package com.projet.stagebackend.service;

import com.projet.stagebackend.entity.Consultation;
import com.projet.stagebackend.entity.MedicalRecords;
import com.projet.stagebackend.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordsService {

    @Autowired
    private MedicalRecordRepository medicalRecordsRepository;

   /* public List<Consultation> getConsultationsByMedicalRecordId(Integer idRecord) {
        Optional<MedicalRecords> medicalRecordsOptional = medicalRecordsRepository.findById(idRecord);
        if (medicalRecordsOptional.isPresent()) {
            MedicalRecords medicalRecords = medicalRecordsOptional.get();
            return medicalRecords.getConsultations();
        } else {
            return Collections.emptyList();
        }
    }*/

}
