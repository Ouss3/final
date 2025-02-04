package com.project.rendezvousservice.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Patient {
    private Long id;


    private String nom;

    private String prenom;


    private String email;

    private LocalDate dateNaissance;


    private String adresse;

    private String telephone;


    private String genre;
}
