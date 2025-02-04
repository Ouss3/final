package com.project.prescriptionsservice.controller;

import com.project.prescriptionsservice.ENTITY.Prescription;
import com.project.prescriptionsservice.model.Patient;
import com.project.prescriptionsservice.model.ReponsePatientDto;
import com.project.prescriptionsservice.repository.PresecriptionRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Project Name: micro
 * File Name: controller
 * Created by: DELL
 * Created on: 2/4/2025
 * Description:
 * <p>
 * controller is a part of the micro project.
 */
@RestController
@RequestMapping("/prescriptions")
public class controller {

    private final PresecriptionRepo presecriptionRepo;
    private final RestTemplate restTemplate;
    @Value("${patient_url}")
    private String patientUrl;

    public controller(PresecriptionRepo presecriptionRepo, RestTemplate restTemplate) {
        this.presecriptionRepo = presecriptionRepo;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = presecriptionRepo.findAll();

        for (Prescription prescription : prescriptions) {
            // get patient by id
            prescription.setPatient(getPatientById(prescription.getPatientId()));

        }
        return prescriptions;
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable  Long id) {
        Prescription prescription = presecriptionRepo.findById(id).orElseThrow();
        // get patient by id
        prescription.setPatient(getPatientById(prescription.getPatientId()));
        return prescription;

    }
    @GetMapping("/patient/{id}")
    public Prescription getPrescriptionByPatientId(@PathVariable Long id) {
        Prescription prescription = presecriptionRepo.findPrescriptionByPatientId(id);
        // get patient by id
        prescription.setPatient(getPatientById(prescription.getPatientId()));
        return prescription;
    }

    @PostMapping("/addprescription")
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        Prescription prescriptionbase= new Prescription();
        if (prescription.getPatientId() == null) {
            throw new IllegalArgumentException("Patient id is required");
        }
        prescriptionbase.setPatientId(prescription.getPatientId());
        prescriptionbase.setMedication(prescription.getMedication());
        prescriptionbase.setDosage(prescription.getDosage());
        prescriptionbase.setStatus(prescription.getStatus());
        prescriptionbase.setFollowUpNotes(prescription.getFollowUpNotes());
        return presecriptionRepo.save(prescriptionbase);



    }

    @PutMapping("/updateprescription/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        Prescription prescriptionbase = presecriptionRepo.findById(id).orElseThrow();
        if (prescription.getPatientId() != null) {
            prescriptionbase.setPatientId(prescription.getPatientId());
        }
        if (prescription.getMedication() != null) {
            prescriptionbase.setMedication(prescription.getMedication());
        }
        if (prescription.getDosage() != null) {
            prescriptionbase.setDosage(prescription.getDosage());
        }
        if (prescription.getStatus() != null) {
            prescriptionbase.setStatus(prescription.getStatus());
        }
        if (prescription.getFollowUpNotes() != null) {
            prescriptionbase.setFollowUpNotes(prescription.getFollowUpNotes());
        }
        return presecriptionRepo.save(prescriptionbase);
    }

    @PutMapping("/updatePreByPatient/{id}")
    public Prescription updatePrescriptionByPatientId(@PathVariable Long id, @RequestBody Prescription prescription) {
        Prescription prescriptionbase = presecriptionRepo.findPrescriptionByPatientId(id);

            prescriptionbase.setPatientId(prescription.getPatientId());

        if (prescription.getMedication() != null) {
            prescriptionbase.setMedication(prescription.getMedication());
        }
        if (prescription.getDosage() != null) {
            prescriptionbase.setDosage(prescription.getDosage());
        }
        if (prescription.getStatus() != null) {
            prescriptionbase.setStatus(prescription.getStatus());
        }
        if (prescription.getFollowUpNotes() != null) {
            prescriptionbase.setFollowUpNotes(prescription.getFollowUpNotes());
        }
        return presecriptionRepo.save(prescriptionbase);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        presecriptionRepo.findById(id).orElseThrow();
        presecriptionRepo.deleteById(id);
    }

    @DeleteMapping("/patient/{id}")
    public void deletePrescriptionByPatientId(@PathVariable Long id) {
        Prescription prescription = presecriptionRepo.findPrescriptionByPatientId(id);
        presecriptionRepo.deleteById(prescription.getId());
    }






    private Patient getPatientById(Long id) {
        ReponsePatientDto reponsePatientDto = restTemplate.getForObject(patientUrl +"/"+ id, ReponsePatientDto.class);
        assert reponsePatientDto != null;
        return reponsePatientDto.getPatient();


    }
}
