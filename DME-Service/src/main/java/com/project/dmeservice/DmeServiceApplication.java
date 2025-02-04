package com.project.dmeservice;

import com.project.dmeservice.entity.MedicalRecord;
import com.project.dmeservice.repositories.MedicalRecordRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class DmeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmeServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(MedicalRecordRepositories productReposetry){
        return args -> {
            MedicalRecord record1 = MedicalRecord.builder()
                    .patientId(1L)
                    .diagnosis("Hypertension")


                    .dateCreated(LocalDateTime.now())
                    .dateModified(LocalDateTime.now())
                    .build();

            productReposetry.save(record1);
            MedicalRecord record2 = MedicalRecord.builder()
                    .patientId(2L)
                    .diagnosis("Diabetes")

                    .dateCreated(LocalDateTime.now())
                    .dateModified(LocalDateTime.now())
                    .build();


            productReposetry.save(record2);




        };
    }
}
