package com.dentaltechapi.resource.resources.user;

import com.dentaltechapi.model.entities.email.EmailModel;
import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.model.entities.user.accountrecovery.AccountRecoveryModel;
import com.dentaltechapi.model.entities.user.dto.*;
import com.dentaltechapi.model.entities.user.session.SessionModel;
import com.dentaltechapi.service.exceptions.AccountRecoveryException;
import com.dentaltechapi.service.exceptions.SessionServiceException;
import com.dentaltechapi.service.exceptions.TokenServiceException;
import com.dentaltechapi.service.exceptions.UserServiceException;
import com.dentaltechapi.service.services.email.EmailService;
import com.dentaltechapi.service.services.token.TokenService;
import com.dentaltechapi.service.services.user.UserService;
import com.dentaltechapi.service.services.user.accountrecovery.AccountRecoveryService;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final SessionService sessionService;
    private final AccountRecoveryService accountRecoveryService;
    private final EmailService emailService;

    public UserResource(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserService userService,
            SessionService sessionService,
            AccountRecoveryService accountRecoveryService,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.sessionService = sessionService;
        this.accountRecoveryService = accountRecoveryService;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginCredentialsDTO userCredentials) {
        try {
            if (!userService.verifyExistingUser(userCredentials.username()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            if (sessionService.verifyExistingSession(userCredentials.username()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este usuário possui uma sessão ativa.");

            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(userCredentials.username(), userCredentials.password());

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

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Authorization", "Bearer " + token)
                    .body(
                            new UserAuthenticatedDataDTO(
                                    authenticatedUser.getUsername(),
                                    authenticatedUser.getEmail(),
                                    authenticatedUser.getRole()
                            )
                    );

        } catch (UserServiceException | TokenServiceException | SessionServiceException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a solicitação.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserLogoutCredentialsDTO user) {
        try {
            if (!sessionService.verifyExistingSession(user.username()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            sessionService.invalidateSession(user.username());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (SessionServiceException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recovery(@RequestBody UserRecoveryDTO user) {
        try {
            if (!userService.verifyExistingUser(user.username()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            if (sessionService.verifyExistingSession(user.username()))
                sessionService.invalidateSession(user.username());

            UserModel registeredUser = userService.findByUsername(user.username());

            AccountRecoveryModel newAccountRecovery =
                    accountRecoveryService.createNewAccountRecovery(
                            new AccountRecoveryModel(
                                    true,
                                    Instant.now(),
                                    Instant.now().plusSeconds(600), //Dez Minutos
                                    registeredUser)
                    );

            emailService.sendEMail(
                    new EmailModel(
                            registeredUser.getEmail(),
                            null,
                            "Recuperação de Conta",
                            "Código: " + newAccountRecovery.getCode()
                    )
            );

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UserServiceException | AccountRecoveryException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/recovery-validation")
    public ResponseEntity<?> recoveryValidation(@RequestBody UserRecoveryValidationDTO recoveryValidation) {
        try {
            if (!accountRecoveryService.verifyExistingAccountRecovery(recoveryValidation.username()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            if (!accountRecoveryService.findAccountRecovery(recoveryValidation.username(), recoveryValidation.code()).getIsValid())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            AccountRecoveryModel registeredAccountRecovery =
                    accountRecoveryService.findAccountRecovery(recoveryValidation.username(), recoveryValidation.code());

            if (Instant.now().isAfter(registeredAccountRecovery.getEndValidity()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("O código expirou.");

            if (!Objects.equals(registeredAccountRecovery.getCode(), recoveryValidation.code())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código incorreto");
            }

            UserModel user = userService.updateUser(registeredAccountRecovery.getUser().getId());

            emailService.sendEMail(
                    new EmailModel(
                            user.getEmail(),
                            "",
                            "Sua Nova Senha",
                            "Senha: " + user.getPassword()
                                    + ". É fortemente recomendado alterar essa senha assim que fizer login novamente."
                    )
            );

            registeredAccountRecovery.setIsValid(false);
            accountRecoveryService.updateAccountRecovery(registeredAccountRecovery);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (UserServiceException | AccountRecoveryException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterData) {
        try {
            if (userService.verifyExistingUser(userRegisterData.username()))
                return ResponseEntity.badRequest().body("O Usuário informado já existe.");

            String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterData.password());

            UserModel newUser = userService.createNewUser(
                    new UserModel(
                            userRegisterData.username(),
                            encryptedPassword,
                            userRegisterData.email(),
                            "user")
            );

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newUser.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();

        } catch (UserServiceException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
