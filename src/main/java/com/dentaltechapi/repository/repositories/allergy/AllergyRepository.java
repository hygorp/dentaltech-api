package com.dentaltechapi.repository.repositories.allergy;

import com.dentaltechapi.model.entities.allergy.AllergyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<AllergyModel, Long> {

}
