package com.dentaltechapi.repository.repositories.specialty;

import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<SpecialtyModel, Long> {
    @NonNull
    Page<SpecialtyModel> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<SpecialtyModel> findAllBySpecialtyContainingIgnoreCase(Pageable pageable, @NonNull String name);
}
