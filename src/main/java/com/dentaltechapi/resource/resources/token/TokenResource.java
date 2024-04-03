package com.dentaltechapi.resource.resources.token;

import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.model.entities.user.dto.UserAuthenticatedDataDTO;
import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.service.exceptions.user.UserNotFoundException;
import com.dentaltechapi.service.services.token.TokenService;
import com.dentaltechapi.service.services.user.UserService;
import com.dentaltechapi.service.services.user.session.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/token")
public class TokenResource {

    private final TokenService tokenService;
    private final UserService userService;
    private final SessionService sessionService;

    public TokenResource(TokenService tokenService, UserService userService, SessionService sessionService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token, @RequestHeader("Session") UUID session) {
        try {
            String username = tokenService.validateToken(token.replace("Bearer ", ""));
            UserModel authenticatedUser = userService.findByUserUsername(username);

            if (authenticatedUser == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            SessionModel currentSession = sessionService.findSessionByID(session);

            if (!currentSession.getIsValid())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            UserAuthenticatedDataDTO userData = new UserAuthenticatedDataDTO(
                    authenticatedUser.getUsername(),
                    authenticatedUser.getEmail(),
                    authenticatedUser.getRole(),
                    currentSession.getToken(),
                    currentSession.getId()
            );

            return ResponseEntity.ok().body(userData);
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
