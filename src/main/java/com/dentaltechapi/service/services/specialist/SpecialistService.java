package com.dentaltechapi.service.services.specialist;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import com.dentaltechapi.model.entities.specialist.dto.SpecialistDTO;
import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.repository.repositories.specialist.SpecialistRepository;
import com.dentaltechapi.service.exceptions.specialist.SpecialistCreationException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistNotFoundException;
import com.dentaltechapi.service.services.officedatetime.OfficeDateTimeService;
import com.dentaltechapi.service.services.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UserService userService;
    private final OfficeDateTimeService officeDateTimeService;

    public SpecialistService(SpecialistRepository specialistRepository, UserService userService, OfficeDateTimeService officeDateTimeService) {
        this.specialistRepository = specialistRepository;
        this.userService = userService;
        this.officeDateTimeService = officeDateTimeService;
    }

    public List<SpecialistDTO> findAllSpecialists() {
        try {
            List<SpecialistModel> listOfSpecialists =  specialistRepository.findAll();
            List<SpecialistDTO> listOfSpecialistDTOS = new ArrayList<>();

            for (SpecialistModel specialist : listOfSpecialists) {
                listOfSpecialistDTOS.add(new SpecialistDTO(
                        specialist.getId(),
                        specialist.getName(),
                        specialist.getSignature(),
                        specialist.getCpfOrCnpj(),
                        specialist.getCro(),
                        specialist.getCroState(),
                        specialist.getCredentials().getEmail(),
                        specialist.getPhones(),
                        specialist.getSpecialties(),
                        specialist.getOfficeDateTime()
                ));
            }

            return listOfSpecialistDTOS;
        } catch (NoSuchElementException exception) {
            throw new SpecialistNotFoundException("Não foram encontrados especialistas.", exception.getCause());
        }
    }

    public void createNewSpecialist(SpecialistModel specialistModel) {
        try {
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

            newSpecialist.setName(specialistModel.getName());
            newSpecialist.setSignature(specialistModel.getSignature());
            newSpecialist.setCpfOrCnpj(specialistModel.getCpfOrCnpj());
            newSpecialist.setCro(specialistModel.getCro());
            newSpecialist.setCroState(specialistModel.getCroState());
            newSpecialist.setCredentials(newUserCredentials);
            newSpecialist.setPhones(specialistModel.getPhones());
            newSpecialist.setSpecialties(specialistModel.getSpecialties());
            newSpecialist.setOfficeDateTime(persistedOfficeDateTimes);

            specialistRepository.save(newSpecialist);
        } catch (IllegalArgumentException exception) {
            throw new SpecialistCreationException("Erro ao salvar especialista.", exception.getCause());
        }
    }
}
