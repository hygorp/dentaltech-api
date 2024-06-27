package com.dentaltechapi.service.services.specialist;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import com.dentaltechapi.model.entities.specialist.dto.SpecialistDTO;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.model.entities.user.dto.UserDTO;
import com.dentaltechapi.repository.repositories.specialist.SpecialistRepository;
import com.dentaltechapi.service.exceptions.specialist.SpecialistCreationException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistNotFoundException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistUpdateException;
import com.dentaltechapi.service.services.officedatetime.OfficeDateTimeService;
import com.dentaltechapi.service.services.specialty.SpecialtyService;
import com.dentaltechapi.service.services.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserService userService;
    private final OfficeDateTimeService officeDateTimeService;
    private final SpecialtyService specialtyService;

    public SpecialistService(SpecialistRepository specialistRepository, UserService userService, OfficeDateTimeService officeDateTimeService, SpecialtyService specialtyService) {
        this.specialistRepository = specialistRepository;
        this.userService = userService;
        this.officeDateTimeService = officeDateTimeService;
        this.specialtyService = specialtyService;
    }

    public Page<SpecialistDTO> findAllSpecialists(Pageable pageable) {
        try {
            Page<SpecialistModel> listOfSpecialists = specialistRepository.findAll(pageable);

            return listOfSpecialists.map(specialist -> new SpecialistDTO(
                    specialist.getId(),
                    specialist.getName(),
                    specialist.getSignature(),
                    specialist.getCpf(),
                    specialist.getCro(),
                    specialist.getCroState(),
                    new UserDTO(
                            specialist.getCredentials().getId(),
                            specialist.getCredentials().getUsername(),
                            specialist.getCredentials().getEmail(),
                            specialist.getCredentials().getRole()
                    ),
                    specialist.getPhones(),
                    specialist.getSpecialties(),
                    specialist.getSchedule()
            ));
        } catch (NoSuchElementException exception) {
            throw new SpecialistNotFoundException("Não foram encontrados especialistas.", exception.getCause());
        }
    }

    public SpecialistDTO findSpecialistById(Long id) {
        try {
            SpecialistModel specialist = specialistRepository.findById(id).orElseThrow(() -> new SpecialistNotFoundException("Especialista não encontrado."));
            return new SpecialistDTO(
                    specialist.getId(),
                    specialist.getName(),
                    specialist.getSignature(),
                    specialist.getCpf(),
                    specialist.getCro(),
                    specialist.getCroState(),
                    new UserDTO(
                            specialist.getCredentials().getId(),
                            specialist.getCredentials().getUsername(),
                            specialist.getCredentials().getEmail(),
                            specialist.getCredentials().getRole()
                    ),
                    specialist.getPhones(),
                    specialist.getSpecialties(),
                    specialist.getSchedule()
            );
        } catch (NoSuchElementException | SpecialistNotFoundException exception) {
            throw new SpecialistNotFoundException("Especialista não encontrado.");
        }
    }

    public Page<SpecialistDTO> filterSpecialistsByNameAndSpecialty(Pageable pageable, String name, Long[] specialties) {
        try {
            Page<SpecialistModel> pageOfSpecialists;

            if (specialties != null && specialties.length > 0)
                pageOfSpecialists =
                        specialistRepository.findDistinctByNameContainingIgnoreCaseAndSpecialtiesIdIn(pageable, name, List.of(specialties));
            else
                pageOfSpecialists =
                        specialistRepository.findAllByNameContainingIgnoreCase(pageable, name);

            List<SpecialistDTO> filteredList =
                    pageOfSpecialists.getContent().stream()
                            .map(this::convertToDto)
                            .collect(Collectors.toList());

            return new PageImpl<>(filteredList, pageable, pageOfSpecialists.getTotalElements());
        } catch (NoSuchElementException exception) {
            throw new SpecialistNotFoundException("Não foram encontrados especialistas.", exception.getCause());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void createNewSpecialist(SpecialistModel specialistModel) {
        try {
            System.out.println(specialistModel.getSpecialties());
            SpecialistModel newSpecialist = new SpecialistModel();

            UserModel newUserCredentials = userService.createNewUser(
                    new UserModel(
                            specialistModel.getCredentials().getUsername(),
                            new BCryptPasswordEncoder().encode(specialistModel.getCredentials().getPassword()),
                            specialistModel.getCredentials().getEmail(),
                            specialistModel.getCredentials().getRole()
                    )
            );

            Set<OfficeDateTimeModel> persistedOfficeDateTimes = new HashSet<>();
            for (OfficeDateTimeModel officeDateTime : specialistModel.getSchedule()) {
                OfficeDateTimeModel persistedOfficeDateTime = officeDateTimeService.createNewOfficeDateTime(officeDateTime);
                persistedOfficeDateTimes.add(persistedOfficeDateTime);
            }

            Set<SpecialtyModel> persistedSpecialties = new HashSet<>();
            for (SpecialtyModel specialty : specialistModel.getSpecialties()) {
                SpecialtyModel persistedSpecialty = specialtyService.findSpecialtyById(specialty.getId());
                if (persistedSpecialty != null) {
                    persistedSpecialties.add(persistedSpecialty);
                } else {
                    throw new IllegalArgumentException("Especialidade não encontrada para o ID: " + specialty.getId());
                }
            }

            newSpecialist.setName(specialistModel.getName());
            newSpecialist.setSignature(specialistModel.getSignature());
            newSpecialist.setCpf(specialistModel.getCpf());
            newSpecialist.setCro(specialistModel.getCro());
            newSpecialist.setCroState(specialistModel.getCroState());
            newSpecialist.setCredentials(newUserCredentials);
            newSpecialist.setPhones(specialistModel.getPhones());
            newSpecialist.setSpecialties(persistedSpecialties);
            newSpecialist.setSchedule(persistedOfficeDateTimes);

            specialistRepository.save(newSpecialist);
        } catch (IllegalArgumentException exception) {
            throw new SpecialistCreationException("Erro ao salvar especialista.", exception.getCause());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateSpecialist(SpecialistModel specialist) {
        try {
            SpecialistModel persistedSpecialist =
                    specialistRepository.findById(specialist.getId()).orElseThrow(() -> new SpecialistNotFoundException("Especialista não encontrado."));

            if (!specialist.getName().isEmpty() || !specialist.getName().isBlank()) {
                if (!Objects.equals(specialist.getName(), persistedSpecialist.getName()))
                    persistedSpecialist.setName(specialist.getName());
            }

            if (!specialist.getSignature().isEmpty() || !specialist.getSignature().isBlank()) {
                if (!Objects.equals(specialist.getSignature(), persistedSpecialist.getSignature()))
                    persistedSpecialist.setSignature(specialist.getSignature());
            }

            if (!specialist.getCpf().isEmpty() || !specialist.getCpf().isBlank()) {
                if (!Objects.equals(specialist.getCpf(), persistedSpecialist.getCpf()))
                    persistedSpecialist.setCpf(specialist.getCpf());
            }

            if (!specialist.getCro().isEmpty() || !specialist.getCro().isBlank()) {
                if (!Objects.equals(specialist.getCro(), persistedSpecialist.getCro()))
                    persistedSpecialist.setCro(specialist.getCro());
            }

            if (!specialist.getCroState().isEmpty() || !specialist.getCroState().isBlank()) {
                if (!Objects.equals(specialist.getCroState(), persistedSpecialist.getCroState()))
                    persistedSpecialist.setCroState(specialist.getCroState());
            }

            if (specialist.getCredentials().getUsername() != null)
                if (!specialist.getCredentials().getUsername().isEmpty() || !specialist.getCredentials().getUsername().isBlank()) {
                    if (!Objects.equals(specialist.getCredentials().getUsername(), persistedSpecialist.getCredentials().getUsername()))
                        persistedSpecialist.getCredentials().setUsername(specialist.getCredentials().getUsername());
                }

            if (specialist.getCredentials().getPassword() != null)
                if (!specialist.getCredentials().getPassword().isEmpty() || !specialist.getCredentials().getPassword().isBlank()) {
                    if (!Objects.equals(specialist.getCredentials().getPassword(), persistedSpecialist.getCredentials().getPassword()))
                        persistedSpecialist.getCredentials().setPassword(specialist.getCredentials().getPassword());
                }

            if (!specialist.getPhones().isEmpty()) {
                persistedSpecialist.setPhones(specialist.getPhones());
            }

            if (!specialist.getSpecialties().isEmpty()) {
                persistedSpecialist.setSpecialties(specialist.getSpecialties());
            }

            if (!specialist.getSchedule().isEmpty()) {
                persistedSpecialist.setSchedule(specialist.getSchedule());
            }

            specialistRepository.save(persistedSpecialist);
        } catch (SpecialistUpdateException exception) {
            throw new SpecialistUpdateException("Erro ao atualizar especialista.");
        }
    }

    public Boolean verifyExistingCpf(String cpf) {
        return specialistRepository.existsByCpf(cpf);
    }

    private SpecialistDTO convertToDto(SpecialistModel specialist) {
        return new SpecialistDTO(
                specialist.getId(),
                specialist.getName(),
                specialist.getSignature(),
                specialist.getCpf(),
                specialist.getCro(),
                specialist.getCroState(),
                new UserDTO(
                        specialist.getCredentials().getId(),
                        specialist.getCredentials().getUsername(),
                        specialist.getCredentials().getEmail(),
                        specialist.getCredentials().getRole()
                ),
                specialist.getPhones(),
                specialist.getSpecialties(),
                specialist.getSchedule()
        );
    }
}
