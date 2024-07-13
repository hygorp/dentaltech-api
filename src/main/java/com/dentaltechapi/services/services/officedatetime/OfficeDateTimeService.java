package com.dentaltechapi.services.services.officedatetime;

import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
import com.dentaltechapi.repository.repositories.officehour.OfficeDateTimeRepository;
import com.dentaltechapi.services.exceptions.officedatetime.OfficeDateTimeCreationException;
import org.springframework.stereotype.Service;

@Service
public class OfficeDateTimeService {

    private final OfficeDateTimeRepository officeDateTimeRepository;

    public OfficeDateTimeService(OfficeDateTimeRepository officeDateTimeRepository) {
        this.officeDateTimeRepository = officeDateTimeRepository;
    }

    public OfficeDateTimeModel createNewOfficeDateTime(OfficeDateTimeModel officeDateTimeModel) {
        try {
            return officeDateTimeRepository.save(officeDateTimeModel);
        } catch (IllegalArgumentException exception) {
            throw new OfficeDateTimeCreationException("Error ao salvar agenda.", exception.getCause());
        }
    }
}
