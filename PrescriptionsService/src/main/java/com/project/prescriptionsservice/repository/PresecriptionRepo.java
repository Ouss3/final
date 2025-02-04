package com.project.prescriptionsservice.repository;

import com.project.prescriptionsservice.ENTITY.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project Name: micro
 * File Name: presecriptionRepo
 * Created by: DELL
 * Created on: 2/4/2025
 * Description:
 * <p>
 * presecriptionRepo is a part of the micro project.
 */

public interface PresecriptionRepo extends JpaRepository<Prescription, Long> {
    Prescription findPrescriptionByPatientId(Long patientId);
}
