package com.parabank.backend.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        // TODO: instanciar con secret="calidad2026" y expirationMs=3600000
        // tokenProvider = new JwtTokenProvider("calidad2026", 3600000L);
    }

    @Test
    void generateToken_retornaTokenNoNulo() {
        // TODO: when tokenProvider.generateToken("testuser")
        // TODO: then token != null && !token.isBlank()
    }

    @Test
    void getUsernameFromToken_tokenValido_retornaUsernameCorrect() {
        // TODO: given token generado para "testuser"
        // TODO: when tokenProvider.getUsernameFromToken(token)
        // TODO: then retorna "testuser"
    }

    @Test
    void validateToken_tokenValido_retornaTrue() {
        // TODO: given token generado con el mismo provider
        // TODO: when tokenProvider.validateToken(token)
        // TODO: then retorna true
    }

    @Test
    void validateToken_tokenExpirado_retornaFalse() {
        // TODO: given tokenProvider con expirationMs=1 (expira inmediatamente)
        // TODO: given token generado y Thread.sleep para asegurar expiración
        // TODO: when tokenProvider.validateToken(token)
        // TODO: then retorna false
    }

    @Test
    void validateToken_tokenMalformado_retornaFalse() {
        // TODO: when tokenProvider.validateToken("not.a.jwt")
        // TODO: then retorna false
    }

    @Test
    void validateToken_tokenVacio_retornaFalse() {
        // TODO: when tokenProvider.validateToken("")
        // TODO: then retorna false
    }

    @Test
    void validateToken_tokenFirmadoConSecretDistinto_retornaFalse() {
        // TODO: given otro JwtTokenProvider con secret diferente genera token
        // TODO: when tokenProvider.validateToken(tokenDeOtroProvider)
        // TODO: then retorna false
    }
}
