package com.dentaltechapi.resource.resources.user;

import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.model.entities.user.dto.UserAuthenticatedDataDTO;
import com.dentaltechapi.model.entities.user.dto.UserLoginCredentialsDTO;
import com.dentaltechapi.model.entities.user.dto.UserRegisterDTO;
import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.service.exceptions.SessionServiceException;
import com.dentaltechapi.service.exceptions.TokenServiceException;
import com.dentaltechapi.service.exceptions.UserServiceException;
import com.dentaltechapi.service.services.token.TokenService;
import com.dentaltechapi.service.services.user.UserService;
import com.dentaltechapi.service.services.user.session.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final SessionService sessionService;

    public UserResource(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService, SessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginCredentialsDTO userCredentials) {
        try {
            if (!userService.verifyExistingUser(userCredentials.username())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            if (sessionService.verifyExistingSession(userCredentials.username())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Este usuário possui uma sessão ativa.");

            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(userCredentials.username(), userCredentials.password());
            Authentication authentication = authenticationManager.authenticate(usernamePassword);
            String token = tokenService.generateToken((UserModel) authentication.getPrincipal());

            UserModel authenticatedUser = userService.findByUsername(userCredentials.username());
            sessionService.createNewSession(
                    new SessionModel(
                            true,
                            Instant.now(),
                            Instant.now().plusSeconds(7200L),
                            token,
                            authenticatedUser
                    )
            );

            return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + token).body(
                    new UserAuthenticatedDataDTO(
                            authenticatedUser.getUsername(),
                            authenticatedUser.getEmail(),
                            authenticatedUser.getRole()
                    )
            );
        } catch (UserServiceException | TokenServiceException | SessionServiceException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a solicitação.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterData) {
        try {
            if (userService.verifyExistingUser(userRegisterData.username())) return ResponseEntity.badRequest().body("O Usuário informado já existe.");

            String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterData.password());
            UserModel newUser = userService.createNewUser(new UserModel(userRegisterData.username(), encryptedPassword, userRegisterData.email(), "user"));

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

            return ResponseEntity.created(uri).build();
        } catch (UserServiceException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
