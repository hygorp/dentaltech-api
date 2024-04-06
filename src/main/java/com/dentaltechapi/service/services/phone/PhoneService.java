package com.dentaltechapi.service.services.phone;

import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.repository.repositories.phone.PhoneRepository;
import com.dentaltechapi.service.exceptions.phone.PhoneCreationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public PhoneModel createNewPhone(PhoneModel phoneModel) {
        try {
            return phoneRepository.save(phoneModel);
        } catch (IllegalArgumentException exception) {
            throw new PhoneCreationException("Erro ao salvar telefone.", exception.getCause());
        }
    }

    public Set<PhoneModel> createNewPhoneList(List<PhoneModel> phoneModels) {
        try {
            return new HashSet<>(phoneRepository.saveAll(phoneModels));
        } catch (IllegalArgumentException exception) {
            throw new PhoneCreationException("Erro ao salvar lista de telefones.", exception.getCause());
        }
    }
}
