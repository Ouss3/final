package com.project.rendezvousservice.repositories;

import com.project.rendezvousservice.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryRendezVous extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findRendezVousByPatientId(Long patientId);


    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM RendezVous r " +
            "WHERE r.patientId = :patientId " +
            "AND ((:dateDebut BETWEEN r.dateDebut AND r.dateFin) " +
            "OR (:dateFin BETWEEN r.dateDebut AND r.dateFin) " +
            "OR (r.dateDebut BETWEEN :dateDebut AND :dateFin) " +
            "OR (r.dateFin BETWEEN :dateDebut AND :dateFin))")
    boolean existsByPatientIdAndDateConflict(@Param("patientId") Long patientId,
                                             @Param("dateDebut") LocalDateTime dateDebut,
                                             @Param("dateFin") LocalDateTime dateFin);


}
