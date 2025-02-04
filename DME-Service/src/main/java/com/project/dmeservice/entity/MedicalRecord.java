package com.project.dmeservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.dmeservice.model.Patient;
import com.project.dmeservice.model.Prescription;
import com.project.dmeservice.model.RendezVous;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(unique = true, nullable = false)
    private Long patientId;
    private String diagnosis;
    @Column(updatable = false)
    private   LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
        dateModified = dateCreated;
    }

    @PreUpdate
    protected void onUpdate() {
        dateModified = LocalDateTime.now();
    }
    @Transient
    private List<Prescription> prescriptions;
    @Transient
    private List<RendezVous> rendezVous;
    @Transient
    private Patient patient;
}

