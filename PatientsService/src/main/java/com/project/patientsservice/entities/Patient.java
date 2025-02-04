package com.project.patientsservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter @ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = true)
    private String adresse;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
