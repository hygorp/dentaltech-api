package com.dentaltechapi.repository.repositories.specialty;

import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyModel, Long> {

}
