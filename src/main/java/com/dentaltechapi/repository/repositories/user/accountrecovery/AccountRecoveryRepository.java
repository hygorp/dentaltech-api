package com.dentaltechapi.repository.repositories.user.accountrecovery;

import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AccountRecoveryRepository extends JpaRepository<AccountRecoveryModel, Long> {
    AccountRecoveryModel findByCode (Integer code);
    List<AccountRecoveryModel> findByIsValidTrue();
    Boolean existsByUser_Username(String username);
}
