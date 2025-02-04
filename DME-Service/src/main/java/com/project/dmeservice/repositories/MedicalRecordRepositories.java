package com.project.dmeservice.repositories;

import com.project.dmeservice.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MedicalRecordRepositories extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord findMedicalRecordByPatientId(Long patientId);
}
