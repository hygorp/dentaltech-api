package com.dentaltechapi.service.services.specialty;

import com.dentaltechapi.repository.repositories.specialty.SpecialtyRepository;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

}
