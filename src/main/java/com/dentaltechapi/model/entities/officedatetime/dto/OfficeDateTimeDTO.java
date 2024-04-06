package com.dentaltechapi.model.entities.officedatetime.dto;

import java.time.LocalTime;
import java.util.List;

public record OfficeDateTimeDTO(
        Long id,
        Integer day,
        List<LocalTime> times
) {
}
