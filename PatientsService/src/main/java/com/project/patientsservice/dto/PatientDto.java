package com.project.patientsservice.dto;

import com.project.patientsservice.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class PatientDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private LocalDate dateNaissance;
    private String telephone;
    private Genre genre;

}
