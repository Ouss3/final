package com.project.patientsservice.services;

import com.project.patientsservice.dto.PatientDto;
import com.project.patientsservice.dto.ReponsePatientDto;
import com.project.patientsservice.entities.Patient;
import com.project.patientsservice.repositories.PatientRepositories;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImp implements PatientService {
    private final PatientRepositories patientRepositories;
    private final ModelMapper modelMapper;

    public PatientServiceImp(PatientRepositories patientRepositories, ModelMapper modelMapper) {
        this.patientRepositories = patientRepositories;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReponsePatientDto getPatients() {
        List<Patient> patients = patientRepositories.findAll();
        List<PatientDto> patientDto = modelMapper.map(patients, new TypeToken<List<PatientDto>>(){}.getType());
        return ReponsePatientDto.builder()
                .patients(patientDto)
                .build();
    }

    @Override
    public ReponsePatientDto getPatientById(Long id) {
        Patient patient = patientRepositories.findById(id).orElseThrow(()->new NotFoundException("Patient not found"));
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        return ReponsePatientDto.builder()
                .patient(patientDto)
                .build();
    }

    @Override
    public ReponsePatientDto addPatient(PatientDto patientDto) {
        Patient newPatient = modelMapper.map(patientDto, Patient.class);
      patientRepositories.save(newPatient);
      return ReponsePatientDto.builder()
              .message("Patient added successfully")
              .build();
    }

    @Override
    public ReponsePatientDto updatePatient(PatientDto patient,Long id) {
        Patient patientrepo = patientRepositories.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        if (patient.getNom() != null) {
            patientrepo.setNom(patient.getNom());
        }
        if (patient.getId() != null) {
            patientrepo.setId(patient.getId());
        }
        if (patient.getGenre() != null) {
            patientrepo.setGenre(patient.getGenre());
        }
        if (patient.getEmail() != null) {
            patientrepo.setEmail(patient.getEmail());
        }
        if (patient.getPrenom() != null) {
            patientrepo.setPrenom(patient.getPrenom());
        }
        if (patient.getTelephone() != null) {
            patientrepo.setTelephone(patient.getTelephone());
        }
        if (patient.getDateNaissance() != null) {
            patientrepo.setDateNaissance(patient.getDateNaissance());
        }
        patientRepositories.save(patientrepo);
        return ReponsePatientDto.builder()
                .message("Patient updated successfully")
                .patient(modelMapper.map(patientrepo, PatientDto.class))
                .build();

    }

    @Override
    public ReponsePatientDto deletePatient(Long id) {
        patientRepositories.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        patientRepositories.deleteById(id);
        return ReponsePatientDto.builder()
                .message("Patient deleted successfully")
                .build();
    }
}
