package com.project.patientsservice;

import com.project.patientsservice.entities.Patient;
import com.project.patientsservice.repositories.PatientRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PatientsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsServiceApplication.class, args);
    }



}
