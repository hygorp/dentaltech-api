package com.dentaltechapi.repository.repositories.user.accountrecovery;

import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRecoveryRepository extends JpaRepository<AccountRecoveryModel, Long> {
    AccountRecoveryModel findByUser_Username(String username);
    Boolean existsByUser_Username(String username);
}
