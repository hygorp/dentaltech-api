package com.dentaltechapi.components;

import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import com.dentaltechapi.service.services.user.accountrecovery.AccountRecoveryService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class AccountRecoveryScheduler {

    private final AccountRecoveryService accountRecoveryService;

    public AccountRecoveryScheduler(AccountRecoveryService accountRecoveryService) {
        this.accountRecoveryService = accountRecoveryService;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void checkAndUpdateValidity() {
        Instant currentInstant = Instant.now();
        List<AccountRecoveryModel> accountRecoveries = accountRecoveryService.findAllValidAccountRecoveries();
        for(AccountRecoveryModel accountRecovery : accountRecoveries) {
            if (currentInstant.isAfter(accountRecovery.getEndValidity())) {
                accountRecovery.setIsValid(false);
                accountRecoveryService.updateAccountRecoveryStatus(accountRecovery);
            }
        }
    }
}
