package com.dentaltechapi.repository.repositories.emergencycontact;

import com.dentaltechapi.model.entities.emergencycontact.EmergencyContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContactModel, Long> {

}
