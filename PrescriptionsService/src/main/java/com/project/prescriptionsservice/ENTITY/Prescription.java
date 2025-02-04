package com.project.prescriptionsservice.ENTITY;

import com.project.prescriptionsservice.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project Name: micro
 * File Name: cla
 * Created by: DELL
 * Created on: 2/4/2025
 * Description:
 * <p>
 * cla is a part of the micro project.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Prescription details

    private String medication;

    private String dosage;


    private Long  patientId;
    @Transient
    private Patient patient;

    // Status can be something like PENDING, VALIDATED, or COMPLETED
    @Enumerated(EnumType.STRING)

    private PrescriptionStatus status;

    // Additional fields like comments or follow-up notes
    private String followUpNotes;
}
