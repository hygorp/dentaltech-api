package com.dentaltechapi.service.services.specialty;

import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.repository.repositories.specialty.SpecialtyRepository;
import com.dentaltechapi.service.exceptions.specialty.SpecialtyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<SpecialtyModel> findAllSpecialties() {
        try {
            return specialtyRepository.findAll();
        } catch (NoSuchElementException exception) {
            throw new SpecialtyNotFoundException("Não foram encontradas especialidades", exception.getCause());
        }
    }

    public SpecialtyModel findSpecialtyById(long id) {
        try {
            return specialtyRepository.findById(id).orElseThrow(() -> new SpecialtyNotFoundException("Especialidade não Encontrada"));
        } catch (NoSuchElementException exception) {
            throw new SpecialtyNotFoundException("Especialidade não encontrada", exception.getCause());
        }
    }

    public Boolean verifyExistingSpecialty(Long id) {
        return specialtyRepository.existsById(id);
    }
}
