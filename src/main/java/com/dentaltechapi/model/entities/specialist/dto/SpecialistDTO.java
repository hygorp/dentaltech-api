package com.dentaltechapi.model.entities.specialist.dto;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.model.entities.user.dto.UserDTO;

import java.util.Set;

public record SpecialistDTO(
        Long id,
        String name,
        String signature,
        String cpf,
        String cro,
        String croState,
        UserDTO credentials,
        Set<PhoneModel> phones,
        Set<SpecialtyModel> specialties,
        Set<OfficeDateTimeModel> schedule
) {
}
