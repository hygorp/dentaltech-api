package com.dentaltechapi.repository.repositories.patient;

import com.dentaltechapi.model.entities.patient.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {

}
