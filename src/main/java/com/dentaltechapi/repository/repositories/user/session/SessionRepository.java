package com.dentaltechapi.repository.repositories.user.session;

import com.dentaltechapi.model.entities.user.session.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    Boolean existsByUser_UsernameAndIsValidTrue(String username);
    SessionModel findByUser_Username(String username);
}
