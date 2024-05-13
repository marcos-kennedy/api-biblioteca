package com.projetos.biblioteca.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.projetos.biblioteca.models.user.Usuario;

@Service
public class GeradorToken {
    @Value("${api.security.token.secret}")
    String secret;
    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("library")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(gerarExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
           throw new RuntimeException("Erro na geração do token: ", exception);
        }
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
            .withIssuer("library")
            .build()
            .verify(token)
            .getSubject();
        } catch (JWTVerificationException exception) {
            return "Erro: " + exception.getMessage();
        }
    }

    private Instant gerarExpiracao() {
        return LocalDateTime.now(ZoneId.of("UTC+3")).plusHours(2).toInstant(ZoneOffset.UTC);
    }

}
