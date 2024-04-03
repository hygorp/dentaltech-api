package com.dentaltechapi.components;

import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.service.services.user.session.SessionService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class SessionValidityScheduler {

    private final SessionService sessionService;
    public SessionValidityScheduler(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void checkAndUpdateValidity() {
        Instant currentInstant = Instant.now();
        List<SessionModel> sessions = sessionService.findAllValidSessions();
        for(SessionModel session : sessions) {
            if (currentInstant.isAfter(session.getEndValidity())) {
                sessionService.updateSessionValidity(session.getId(), false);
            }
        }
    }
}
