package com.dentaltechapi.repository.repositories.anamnesis;

import com.dentaltechapi.model.entities.anamnesis.AnamnesisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnamnesisRepository extends JpaRepository<AnamnesisModel, Long> {

}
