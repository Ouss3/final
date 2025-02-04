package com.project.dmeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RendezVous {
    private Long id;
    //private Long docteurId;
    private Long patientId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
}
