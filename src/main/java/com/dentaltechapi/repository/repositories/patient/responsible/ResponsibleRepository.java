package com.dentaltechapi.repository.repositories.patient.responsible;

import com.dentaltechapi.model.entities.patient.responsible.ResponsibleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibleRepository extends JpaRepository<ResponsibleModel, Long> {

}
