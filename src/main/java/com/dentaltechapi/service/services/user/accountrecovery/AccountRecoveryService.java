package com.dentaltechapi.service.services.user.accountrecovery;

import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import com.dentaltechapi.repository.repositories.user.accountrecovery.AccountRecoveryRepository;
import com.dentaltechapi.service.exceptions.user.accountrecovery.AccountRecoveryCreationException;
import com.dentaltechapi.service.exceptions.user.accountrecovery.AccountRecoveryNotFoundException;
import com.dentaltechapi.service.exceptions.user.accountrecovery.AccountRecoveryUpdateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountRecoveryService {

    private final AccountRecoveryRepository accountRecoveryRepository;

    public AccountRecoveryService(AccountRecoveryRepository accountRecoveryRepository) {
        this.accountRecoveryRepository = accountRecoveryRepository;
    }

    public AccountRecoveryModel findAccountRecoveryByCode(Integer code) {
        try {
            return accountRecoveryRepository.findByCode(code);
        } catch (NoSuchElementException exception) {
            throw new AccountRecoveryNotFoundException("Recuperação de conta não encontrada.", exception.getCause());
        }
    }

    public AccountRecoveryModel findAccountByCodeAndUsername(Integer code, String username) {
        try {
            return accountRecoveryRepository.findByCodeAndUser_Username(code, username);
        } catch (NoSuchElementException exception) {
            throw new AccountRecoveryNotFoundException("Recuperação de conta não encontrada.", exception.getCause());
        }
    }

    public List<AccountRecoveryModel> findAllValidAccountRecoveries() {
        try {
            return accountRecoveryRepository.findByIsValidTrue();
        } catch (NoSuchElementException exception) {
            throw new AccountRecoveryNotFoundException("Recuperação de conta não encontrada.", exception.getCause());
        }
    }

    public AccountRecoveryModel createNewAccountRecovery(AccountRecoveryModel accountRecoveryModel) {
        try {
            return accountRecoveryRepository.save(accountRecoveryModel);
        } catch (IllegalArgumentException exception) {
            throw new AccountRecoveryCreationException("Erro ao criar recuperação de conta.", exception.getCause());
        }
    }

    public void updateAccountRecoveryStatus(AccountRecoveryModel accountRecoveryModel) {
        try {
            AccountRecoveryModel accountRecovery = accountRecoveryRepository.findByCode(accountRecoveryModel.getCode());
            accountRecoveryRepository.save(accountRecovery);
        } catch (AccountRecoveryNotFoundException | IllegalArgumentException exception) {
            throw new AccountRecoveryUpdateException("Erro ao atualizar recuperação de conta.", exception.getCause());
        }
    }

    public Boolean verifyExistingAccountRecovery(String username) {
        return accountRecoveryRepository.existsByUser_Username(username);
    }
}
