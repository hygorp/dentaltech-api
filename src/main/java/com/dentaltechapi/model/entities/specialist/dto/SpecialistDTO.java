package com.dentaltechapi.model.entities.specialist.dto;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;

import java.util.Set;

public record SpecialistDTO(
        Long id,
        String name,
        String signature,
        String cpfOrCnpj,
        String cro,
        String croState,
        String email,
        Set<PhoneModel> phones,
        Set<SpecialtyModel> specialties,
        Set<OfficeDateTimeModel> officeDateTime
) {
}
