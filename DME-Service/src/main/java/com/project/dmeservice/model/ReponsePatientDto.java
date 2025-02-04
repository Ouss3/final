package com.project.dmeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project Name: projectspring
 * File Name: d
 * Created by: DELL
 * Created on: 1/22/2025
 * Description:
 * <p>
 * d is a part of the projectspring project.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReponsePatientDto {
    private String message;
    private Patient patient;
    private List<Patient> patients;
}
