package com.dentaltechapi.repository.repositories.treatment;

import com.dentaltechapi.model.entities.treatment.TreatmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<TreatmentModel, Long> {

}
