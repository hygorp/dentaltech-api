package com.dentaltechapi.repository.repositories.tooth;

import com.dentaltechapi.model.entities.tooth.ToothModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToothRepository extends JpaRepository<ToothModel, Long> {

}
