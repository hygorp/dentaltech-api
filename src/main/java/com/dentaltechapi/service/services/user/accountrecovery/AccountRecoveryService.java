package com.dentaltechapi.service.services.user.accountrecovery;

import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import com.dentaltechapi.repository.repositories.user.accountrecovery.AccountRecoveryRepository;
import com.dentaltechapi.service.exceptions.AccountRecoveryException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AccountRecoveryService {

    private final AccountRecoveryRepository accountRecoveryRepository;

    public AccountRecoveryService(AccountRecoveryRepository accountRecoveryRepository) {
        this.accountRecoveryRepository = accountRecoveryRepository;
    }

    public AccountRecoveryModel findAccountRecovery(String username, Integer code) {
        try {
            return accountRecoveryRepository.findByUser_UsernameAndCode(username, code);
        } catch (NoSuchElementException exception) {
            throw new AccountRecoveryException("Recuperação de conta inválida");
        }
    }

    public AccountRecoveryModel createNewAccountRecovery(AccountRecoveryModel accountRecoveryModel) {
        try {
            return accountRecoveryRepository.save(accountRecoveryModel);
        } catch (IllegalArgumentException exception) {
            throw new AccountRecoveryException("Erro ao criar recuperação de conta.", exception.getCause());
        }
    }

    public void updateAccountRecovery(AccountRecoveryModel accountRecoveryModel) {
        try {
            AccountRecoveryModel accountRecovery = accountRecoveryRepository.findById(accountRecoveryModel.getId())
                    .orElseThrow(() -> new AccountRecoveryException("Recuperação de conta não encontrada."));
            accountRecoveryRepository.save(accountRecovery);
        } catch (IllegalArgumentException exception) {
            throw new AccountRecoveryException("Erro ao atualizar recuperação de conta.", exception.getCause());
        }
    }

    public Boolean verifyExistingAccountRecovery(String username) {
        return accountRecoveryRepository.existsByUser_Username(username);
    }
}
