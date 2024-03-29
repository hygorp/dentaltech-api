package com.dentaltechapi.repository.repositories.odontogram;

import com.dentaltechapi.model.entities.odontogram.OdontogramModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontogramRepository extends JpaRepository<OdontogramModel, Long> {

}
