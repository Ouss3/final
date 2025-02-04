package com.project.rendezvousservice;

import com.project.rendezvousservice.entities.RendezVous;
import com.project.rendezvousservice.repositories.RepositoryRendezVous;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication

public class RendezVousServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RendezVousServiceApplication.class, args);
    }



}
