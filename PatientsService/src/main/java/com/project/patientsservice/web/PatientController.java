package com.project.patientsservice.web;

import com.project.patientsservice.dto.PatientDto;
import com.project.patientsservice.dto.ReponsePatientDto;
import com.project.patientsservice.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

     @GetMapping("/patients")
    public ResponseEntity<ReponsePatientDto>getAllPatients(){
        return ResponseEntity.ok(patientService.getPatients());
     }
     @GetMapping("/{id}")
    public ResponseEntity<ReponsePatientDto> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    @PostMapping("/addpatient")
    public ResponseEntity<ReponsePatientDto> addPatient(@RequestBody PatientDto patientDto){
        return ResponseEntity.ok(patientService.addPatient(patientDto));
    }
    @PutMapping("/updatepatient/{id}")
    public ResponseEntity<ReponsePatientDto> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto){
        return ResponseEntity.ok(patientService.updatePatient(patientDto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReponsePatientDto> deletePatient(@PathVariable Long id){
        return ResponseEntity.ok(patientService.deletePatient(id));
    }
}
