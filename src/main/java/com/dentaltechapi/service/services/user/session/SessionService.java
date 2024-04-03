package com.dentaltechapi.service.services.user.session;

import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.repository.repositories.user.session.SessionRepository;
import com.dentaltechapi.service.exceptions.user.session.SessionCreationException;
import com.dentaltechapi.service.exceptions.user.session.SessionNotFoundException;
import com.dentaltechapi.service.exceptions.user.session.SessionUpdateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionModel> findAllValidSessions() {
        return sessionRepository.findAllByIsValidTrue();
    }

    public SessionModel findSessionByID(UUID id) {
        return sessionRepository.findById(id).orElseThrow();
    }

    public SessionModel createNewSession(SessionModel session) {
        try {
            return sessionRepository.save(session);
        } catch (IllegalArgumentException exception) {
            throw new SessionCreationException("Erro ao criar sessão", exception.getCause());
        }
    }

    public void updateSessionValidity(UUID id, Boolean validity) {
        try {
            SessionModel session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException("Sessão não encontrada."));
            session.setIsValid(validity);
            sessionRepository.save(session);
        } catch (SessionNotFoundException | IllegalArgumentException exception) {
            throw new SessionUpdateException("Erro ao atualizar sessão.", exception.getCause());
        }
    }

    public void invalidateSession(UUID id) {
        try {
            SessionModel session = sessionRepository.findById(id).orElseThrow();
            session.setIsValid(false);

            sessionRepository.save(session);
        } catch (NoSuchElementException exception) {
            throw new SessionNotFoundException("Erro ao encontrar sessão.", exception.getCause());
        }
    }

    public Boolean verifyExistingSession(String username) {
        return sessionRepository.existsByUser_UsernameAndIsValidTrue(username);
    }
}
