package com.project.patientsservice.services;

import com.project.patientsservice.dto.PatientDto;
import com.project.patientsservice.dto.ReponsePatientDto;
import com.project.patientsservice.entities.Patient;

import java.util.List;

public interface PatientService {
    ReponsePatientDto getPatients();
    ReponsePatientDto getPatientById(Long id);
    ReponsePatientDto addPatient(PatientDto patient);
    ReponsePatientDto updatePatient(PatientDto patient, Long id);
    ReponsePatientDto deletePatient(Long id);
}
