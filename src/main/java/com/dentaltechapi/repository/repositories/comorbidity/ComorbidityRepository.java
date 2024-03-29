package com.dentaltechapi.repository.repositories.comorbidity;

import com.dentaltechapi.model.entities.comorbidity.ComorbidityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComorbidityRepository extends JpaRepository<ComorbidityModel, Long> {

}
