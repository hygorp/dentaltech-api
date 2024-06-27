package com.dentaltechapi.repository.repositories.phone;

import com.dentaltechapi.model.entities.phone.PhoneModel;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneModel, Long> {
    boolean existsById (@NonNull Long id);
}
