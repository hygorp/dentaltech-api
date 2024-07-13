package com.dentaltechapi.services.services.specialty;

import com.dentaltechapi.exceptions.SpecialtyException;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.repository.repositories.specialty.SpecialtyRepository;
import com.dentaltechapi.services.exceptions.specialty.SpecialtyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Page<SpecialtyModel> findAll(Pageable pageable) {
        try {
            return specialtyRepository.findAll(pageable);
        } catch (NoSuchElementException exception) {
            throw new SpecialtyNotFoundException("Não foram encontradas especialidades.", exception.getCause());
        }
    }

    public Page<SpecialtyModel> findByFilters(Pageable pageable, String name) {
        try {
            return specialtyRepository.findAllBySpecialtyContainingIgnoreCase(pageable, name);
        } catch (NoSuchElementException exception) {
            throw new SpecialtyException("Especialidade não encontrada.", exception.getCause());
        }
    }

    public SpecialtyModel findById(long id) {
        try {
            return specialtyRepository.findById(id).orElseThrow(() -> new SpecialtyNotFoundException("Especialidade não Encontrada."));
        } catch (NoSuchElementException exception) {
            throw new SpecialtyNotFoundException("Especialidade não encontrada.", exception.getCause());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SpecialtyModel save(SpecialtyModel specialtyModel) {
        try {
            return specialtyRepository.save(specialtyModel);
        } catch (IllegalArgumentException exception) {
            throw new SpecialtyException("Houve um erro ao tentar salvar a especialidade.", exception);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SpecialtyModel update(SpecialtyModel specialtyModel, long id) {
        try {
            SpecialtyModel specialty = specialtyRepository.findById(id).orElseThrow(() -> new SpecialtyException("Especialidade não Encontrada."));
            specialty.setSpecialty(specialtyModel.getSpecialty());
            specialty.setDescription(specialtyModel.getDescription());

            return specialtyRepository.save(specialty);
        } catch (IllegalArgumentException exception) {
            throw new SpecialtyException("Houve um erro ao tentar atualizar especialidade.", exception);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void delete(long id) {
        try {
            specialtyRepository.deleteById(id);
        } catch (IllegalArgumentException exception) {
            throw new SpecialtyException("Houve um erro ao tentar deletar a especialidade.", exception);
        }
    }

    public Boolean verifyExistingSpecialty(Long id) {
        return specialtyRepository.existsById(id);
    }
}
