package com.project.prescriptionsservice;

import com.project.prescriptionsservice.ENTITY.Prescription;
import com.project.prescriptionsservice.ENTITY.PrescriptionStatus;
import com.project.prescriptionsservice.repository.PresecriptionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrescriptionsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrescriptionsServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(PresecriptionRepo prescriptionRepository) {
        return args -> {
            Prescription prescription1 = Prescription.builder()
                    .medication("Lisinopril")
                    .dosage("10mg daily")
                    .patientId(1L)
                    .status(PrescriptionStatus.PENDING)
                    .followUpNotes("Monitor blood pressure weekly.")
                    .build();

            prescriptionRepository.save(prescription1);

            Prescription prescription2 = Prescription.builder()
                    .medication("Metformin")
                    .dosage("500mg twice daily")
                    .patientId(2L)
                    .status(PrescriptionStatus.VALIDATED)
                    .followUpNotes("Check blood sugar levels bi-weekly.")
                    .build();

            prescriptionRepository.save(prescription2);
        };}
}
