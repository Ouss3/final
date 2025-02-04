package com.project.dmeservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.dmeservice.entity.MedicalRecord;
import com.project.dmeservice.model.Patient;
import com.project.dmeservice.model.Prescription;
import com.project.dmeservice.model.RendezVous;
import com.project.dmeservice.model.ReponsePatientDto;
import com.project.dmeservice.repositories.MedicalRecordRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/medical")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecordController {
    private final MedicalRecordRepositories medicalRecord;
    private final RestTemplate restTemplate;
    @Value("${patient_url}")
    private String patientUrl;
    @Value("${rendezvous_url}")
    private String rendezvousUrl;

    public MedicalRecordController(MedicalRecordRepositories medicalRecord, RestTemplate restTemplate) {
        this.medicalRecord = medicalRecord;
        this.restTemplate = restTemplate;
    }
    @GetMapping("/all")
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecord.findAll();
        for (MedicalRecord medicalRecord : medicalRecords) {
            // get patient by id

            medicalRecord.setPatient(getPatientById(medicalRecord.getPatientId()));

            // get All rendezvous by patient id


            medicalRecord.setRendezVous(getRendezVousByPatientId(medicalRecord.getPatientId()));

            // get All prescriptions by patient id
            // List<Prescription> prescriptions = getPrescriptionsByPatientId(medicalRecord.getPatientId());
            // medicalRecord.setPrescriptions(prescriptions);


        }
        return medicalRecords;
    }
    @GetMapping("/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable  Long id) {
        MedicalRecord medicalRecord1 = medicalRecord.findById(id).orElseThrow();
        // get patient by id

        medicalRecord1.setPatient(getPatientById(medicalRecord1.getPatientId()));

        // get All rendezvous by patient id

        medicalRecord1.setRendezVous(getRendezVousByPatientId(medicalRecord1.getPatientId()));

            // List<Prescription> prescriptions = getPrescriptionsByPatientId(medicalRecord1.getPatientId());
            // medicalRecord1.setPrescriptions(prescriptions);

        return medicalRecord1;
    }

    @GetMapping("/patient/{id}")
    public List<MedicalRecord> getMedicalRecordByPatientId(@PathVariable Long id) {
        List<MedicalRecord> medicalRecords = medicalRecord.findMedicalRecordByPatientId(id);
        for (MedicalRecord medicalRecord : medicalRecords) {
            // get patient by id

            medicalRecord.setPatient(getPatientById(medicalRecord.getPatientId()));

            // get All rendezvous by patient id

            medicalRecord.setRendezVous(getRendezVousByPatientId(medicalRecord.getPatientId()));

            // get All prescriptions by patient id
            // List<Prescription> prescriptions = getPrescriptionsByPatientId(medicalRecord.getPatientId());
            // medicalRecord.setPrescriptions(prescriptions);

        }
        return medicalRecords;
    }

    @PostMapping("/add")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        if (medicalRecord.getPatientId() == null) {
            throw new IllegalArgumentException("Patient id is required");
        }
        if (!patientExists(medicalRecord.getPatientId())) {
            throw new IllegalArgumentException("Patient does not exist");
        }
        if (medicalRecord.getDiagnosis() == null) {
            throw new IllegalArgumentException("Diagnosis is required");
        }
        medicalRecord1.setDiagnosis(medicalRecord.getDiagnosis());
        medicalRecord1.setPatientId(medicalRecord.getPatientId());
        medicalRecord1.setDateCreated(LocalDateTime.now());


        return this.medicalRecord.save(medicalRecord1);



    }




    private List<RendezVous> getRendezVousByPatientId(Long id) {
        ResponseEntity<List<RendezVous>> response = restTemplate.exchange(
                rendezvousUrl + "/patient/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RendezVous>>() {}
        );
        return response.getBody();
    }
    private Patient getPatientById(Long id) {
        ReponsePatientDto reponsePatientDto = restTemplate.getForObject(patientUrl +"/"+ id, ReponsePatientDto.class);
        assert reponsePatientDto != null;
        return reponsePatientDto.getPatient();


    }
    // check if the patient exists
    private boolean patientExists(Long id) {
        ReponsePatientDto reponsePatientDto = restTemplate.getForObject(patientUrl +"/"+ id, ReponsePatientDto.class);

        return reponsePatientDto != null && reponsePatientDto.getPatient() != null;
    }
//    private List<Prescription> getPrescriptionsByPatientId(Long id) {
//        ResponseEntity<List<Prescription>> response = restTemplate.exchange(
//                prescriptionUrl + "/patient/" + id,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Prescription>>() {}
//        );
//        return response.getBody();
//    }

}
