package com.dickel.movieflix.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dickel.movieflix.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenService {

    @Value("${movieflix.security.secret}")
    private String secret;
    private Algorithm algorithm;
    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret); // agora o secret já foi injetado
    }

    public String generateToken(User user) {
        // a partir dos dados, eu gero um token (jwt)
        return JWT.create()
                .withSubject(user.getEmail()) //meu username
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plusSeconds(86400)) // 24 horas
                .withIssuedAt(Instant.now()) // quando foi gerado
                .withIssuer("API Movieflix") // quem gerou
                .sign(algorithm); // assino com o algoritmo e a secret
    }
}
