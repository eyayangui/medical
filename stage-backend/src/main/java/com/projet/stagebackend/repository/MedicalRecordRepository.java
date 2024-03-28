package com.projet.stagebackend.repository;

import com.projet.stagebackend.entity.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecords, Integer> {
}
