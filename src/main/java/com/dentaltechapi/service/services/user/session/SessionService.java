package com.dentaltechapi.service.services.user.session;

import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.repository.repositories.user.session.SessionRepository;
import com.dentaltechapi.service.exceptions.SessionServiceException;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void createNewSession(SessionModel session) {
        try {
            sessionRepository.save(session);
        } catch (IllegalArgumentException  exception) {
            throw new SessionServiceException("Erro ao criar sessão", exception.getCause());
        }
    }

    public Boolean verifyExistingSession(String username) {
        return sessionRepository.existsByUser_UsernameAndIsValidTrue(username);
    }
}
