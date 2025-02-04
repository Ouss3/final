package com.project.rendezvousservice.web;

import com.project.rendezvousservice.entities.RendezVous;
import com.project.rendezvousservice.model.Patient;
import com.project.rendezvousservice.model.ReponsePatientDto;
import com.project.rendezvousservice.repositories.RepositoryRendezVous;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/rendezVous")
public class Controller {
    private final RepositoryRendezVous repositoryRendezVous;

    private final RestTemplate restTemplate;
    @Value("${patient.service.url}")
    private String patienturl;

    public Controller(RepositoryRendezVous repositoryRendezVous, RestTemplate restTemplate) {
        this.repositoryRendezVous = repositoryRendezVous;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public List<RendezVous> all() {
        List<RendezVous> rendezVous = repositoryRendezVous.findAll();
        for (RendezVous render : rendezVous ) {
            ReponsePatientDto reponsepatient = restTemplate.getForObject(patienturl+"/"+render.getPatientId(), ReponsePatientDto.class);
            assert reponsepatient != null;
            Patient patient = reponsepatient.getPatient();
             render.setPatient(patient);

        }
        return rendezVous;
    }

    @GetMapping("/{id}")
    public RendezVous getRendezVousById(@PathVariable Long id) {
        RendezVous rendezVous = repositoryRendezVous.findById(id).orElseThrow(()->new NotFoundException("RendezVous not found"));
        ReponsePatientDto reponsepatient = restTemplate.getForObject(patienturl+"/"+rendezVous.getPatientId(), ReponsePatientDto.class);
        assert reponsepatient != null;
        Patient patient = reponsepatient.getPatient();
        rendezVous.setPatient(patient);
        return rendezVous;
    }

    @GetMapping("/patient/{id}")

    public List<RendezVous> getRendezVousByPatientId(@PathVariable Long id) {
        List<RendezVous> rendezVous = repositoryRendezVous.findRendezVousByPatientId(id);
        for (RendezVous render : rendezVous ) {
            ReponsePatientDto reponsepatient = restTemplate.getForObject(patienturl+"/"+render.getPatientId(), ReponsePatientDto.class);
            assert reponsepatient != null;
            Patient patient = reponsepatient.getPatient();
            render.setPatient(patient);

        }
        return rendezVous;
    }
    @Transactional
    @PostMapping("/addrendezvous")
    public ResponseEntity<?> addRendezVous(@RequestBody RendezVous rendezVous) {
        // Check if there are conflicting appointments
        boolean isConflict = repositoryRendezVous.existsByPatientIdAndDateConflict(
                rendezVous.getPatientId(),
                rendezVous.getDateDebut(),
                rendezVous.getDateFin()
        );

        if (isConflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("The requested appointment time is already booked.");
        }

        // Save the appointment
        RendezVous newRendezVous = repositoryRendezVous.save(rendezVous);

        // Fetch patient details using RestTemplate
        ReponsePatientDto reponsePatient = restTemplate.getForObject(patienturl + "/" + newRendezVous.getPatientId(), ReponsePatientDto.class);

        // Ensure the response is not null and set patient details
        if (reponsePatient != null && reponsePatient.getPatient() != null) {
            newRendezVous.setPatient(reponsePatient.getPatient());
        }

        return ResponseEntity.ok(newRendezVous);
    }

    @PutMapping("/updaterendezvous/{id}")
    public ResponseEntity<?> updateRendezVous(@PathVariable Long id, @RequestBody RendezVous rendezVous) {
        // Check if the appointment exists
        RendezVous existingRendezVous = repositoryRendezVous.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        // Check if there are conflicting appointments
        boolean isConflict = repositoryRendezVous.existsByPatientIdAndDateConflict(
                rendezVous.getPatientId(),
                rendezVous.getDateDebut(),
                rendezVous.getDateFin()
        );

        if (isConflict) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("The requested appointment time is already booked.");
        }

        // Update the appointment
        existingRendezVous.setPatientId(rendezVous.getPatientId());
        existingRendezVous.setDateDebut(rendezVous.getDateDebut());
        existingRendezVous.setDateFin(rendezVous.getDateFin());

        // Save the updated appointment
        RendezVous updatedRendezVous = repositoryRendezVous.save(existingRendezVous);

        // Fetch patient details using RestTemplate
        ReponsePatientDto reponsePatient = restTemplate.getForObject(patienturl + "/" + updatedRendezVous.getPatientId(), ReponsePatientDto.class);

        // Ensure the response is not null and set patient details
        if (reponsePatient != null && reponsePatient.getPatient() != null) {
            updatedRendezVous.setPatient(reponsePatient.getPatient());
        }

        return ResponseEntity.ok(updatedRendezVous);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRendezVous(@PathVariable Long id) {
        // Check if the appointment exists
        RendezVous existingRendezVous = repositoryRendezVous.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        // Delete the appointment
        repositoryRendezVous.deleteById(id);

        return ResponseEntity.ok("Appointment deleted successfully");
    }

    @DeleteMapping("patient/{id}")
    public ResponseEntity<?> deleteRendezVousByPatientId(@PathVariable Long id) {
        // Check if the appointment exists
        List<RendezVous> existingRendezVous = repositoryRendezVous.findRendezVousByPatientId(id);

        // Delete the appointment
        repositoryRendezVous.deleteAll(existingRendezVous);

        return ResponseEntity.ok("Appointments deleted successfully");
    }





}
