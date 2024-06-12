package com.dentaltechapi.repository.repositories.specialist;

import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistModel, Long> {
    @NonNull
    Page<SpecialistModel> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<SpecialistModel> findAllByNameContainingIgnoreCase(Pageable pageable, @NonNull String name);

    @NonNull
    Page<SpecialistModel> findDistinctByNameContainingIgnoreCaseAndSpecialtiesIdIn(@NonNull Pageable pageable, String name, Collection<Long> specialtiesId);

    Boolean existsByCpf(String cpf);
}
