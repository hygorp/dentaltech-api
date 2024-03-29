package com.dentaltechapi.repository.repositories.procedure;

import com.dentaltechapi.model.entities.procedure.ProcedureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<ProcedureModel, Long> {

}
