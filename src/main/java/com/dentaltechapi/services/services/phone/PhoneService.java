package com.dentaltechapi.services.services.phone;

import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.repository.repositories.phone.PhoneRepository;
import com.dentaltechapi.services.exceptions.phone.PhoneCreationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public PhoneModel findById(Long id) {
        return phoneRepository.findById(id).orElse(null);
    }

    public PhoneModel createNewPhone(PhoneModel phoneModel) {
        try {
            return phoneRepository.save(phoneModel);
        } catch (IllegalArgumentException exception) {
            throw new PhoneCreationException("Erro ao salvar telefone.", exception.getCause());
        }
    }

    public Set<PhoneModel> createNewPhoneList(Set<PhoneModel> phoneModels) {
        try {
            return new HashSet<>(phoneRepository.saveAll(phoneModels));
        } catch (IllegalArgumentException exception) {
            throw new PhoneCreationException("Erro ao salvar lista de telefones.", exception.getCause());
        }
    }

    public void updatePhone(PhoneModel phoneModel) {
        try {
            phoneRepository.save(phoneModel);
        } catch (IllegalArgumentException exception) {
            throw new PhoneCreationException("Erro ao salvar telefone.", exception.getCause());
        }
    }

    public void deletePhone(Long id) {
        try {
            phoneRepository.deleteById(id);
        } catch (IllegalArgumentException exception) {
            //
            throw new PhoneCreationException("Erro ao salvar telefone.", exception.getCause());
        }
    }

    public Boolean verifyExistingPhone(Long id) {
        return phoneRepository.existsById(id);
    }
}
