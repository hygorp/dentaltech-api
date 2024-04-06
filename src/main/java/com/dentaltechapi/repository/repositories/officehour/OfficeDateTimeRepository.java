package com.dentaltechapi.repository.repositories.officehour;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeDateTimeRepository extends JpaRepository<OfficeDateTimeModel, Long> {
}
