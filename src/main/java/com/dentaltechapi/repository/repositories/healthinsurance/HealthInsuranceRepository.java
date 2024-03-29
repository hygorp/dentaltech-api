package com.dentaltechapi.repository.repositories.healthinsurance;

import com.dentaltechapi.model.entities.healthinsurance.HealthInsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsuranceModel, Long> {

}
