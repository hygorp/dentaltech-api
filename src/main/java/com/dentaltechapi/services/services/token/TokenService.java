package com.dentaltechapi.services.services.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.services.exceptions.token.TokenGenerationException;
import com.dentaltechapi.services.exceptions.token.TokenValidationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String secretKey = "D3nt@lT3ch";

    public String generateToken(UserModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("dentaltech")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDateTime())
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new TokenGenerationException("Erro ao Gerar Token.", exception.getCause());
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("dentaltech")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception) {
            throw new TokenValidationException("Erro ao Validar Token.", exception.getCause());
        }
    }

    private Instant generateExpirationDateTime() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }
}
