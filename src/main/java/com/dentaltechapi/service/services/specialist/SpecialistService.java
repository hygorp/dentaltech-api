package com.dentaltechapi.service.services.specialist;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import com.dentaltechapi.model.entities.specialist.dto.SpecialistDTO;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.repository.repositories.specialist.SpecialistRepository;
import com.dentaltechapi.service.exceptions.specialist.SpecialistCreationException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistNotFoundException;
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

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
                    specialist.getCredentials().getEmail(),
                    specialist.getPhones(),
                    specialist.getSpecialties(),
                    specialist.getOfficeDateTime()
            ));
        } catch (NoSuchElementException exception) {
            throw new SpecialistNotFoundException("Não foram encontrados especialistas.", exception.getCause());
        }
    }

    public SpecialistDTO findSpecialistById(Long id) {
        try {
            SpecialistModel specialistModel = specialistRepository.findById(id).orElseThrow(() -> new SpecialistNotFoundException("Especialista não encontrado."));
            return new SpecialistDTO(
                    specialistModel.getId(),
                    specialistModel.getName(),
                    specialistModel.getSignature(),
                    specialistModel.getCpf(),
                    specialistModel.getCro(),
                    specialistModel.getCroState(),
                    specialistModel.getCredentials().getEmail(),
                    specialistModel.getPhones(),
                    specialistModel.getSpecialties(),
                    specialistModel.getOfficeDateTime()
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
            for (OfficeDateTimeModel officeDateTime : specialistModel.getOfficeDateTime()) {
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
            newSpecialist.setOfficeDateTime(persistedOfficeDateTimes);

            specialistRepository.save(newSpecialist);
        } catch (IllegalArgumentException exception) {
            throw new SpecialistCreationException("Erro ao salvar especialista.", exception.getCause());
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
                specialist.getCredentials().getEmail(),
                specialist.getPhones(),
                specialist.getSpecialties(),
                specialist.getOfficeDateTime()
        );
    }
}
