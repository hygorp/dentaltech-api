package com.dentaltechapi.repository.repositories.user.session;

import com.dentaltechapi.model.entities.user.session.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, UUID> {
    Boolean existsByUser_UsernameAndIsValidTrue(String username);
    List<SessionModel> findAllByIsValidTrue();
}
