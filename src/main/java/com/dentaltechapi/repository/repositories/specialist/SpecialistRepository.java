package com.dentaltechapi.repository.repositories.specialist;

import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistModel, Long> {

}
