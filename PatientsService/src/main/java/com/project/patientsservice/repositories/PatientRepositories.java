package com.project.patientsservice.repositories;

import com.project.patientsservice.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositories extends JpaRepository<Patient, Long> {

}
