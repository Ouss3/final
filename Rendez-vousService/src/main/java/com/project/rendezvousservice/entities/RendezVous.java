package com.project.rendezvousservice.entities;

import com.project.rendezvousservice.model.Docteur;
import com.project.rendezvousservice.model.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter @ToString

public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long docteurId;
    private Long patientId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    //@Transient
    //private Docteur docteur;
    @Transient
    private Patient patient;

}
