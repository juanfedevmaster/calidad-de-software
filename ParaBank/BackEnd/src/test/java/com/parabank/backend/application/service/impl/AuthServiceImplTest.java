package com.parabank.backend.application.service.impl;

import com.parabank.backend.application.security.TokenProvider;
import com.parabank.backend.domain.exception.InvalidCredentialsException;
import com.parabank.backend.domain.model.User;
import com.parabank.backend.domain.repository.UserRepository;
import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest validRequest;
    private User existingUser;

    @BeforeEach
    void setUp() {
        // TODO: inicializar validRequest y existingUser con datos de prueba
    }

    @Test
    void login_credencialesValidas_retornaAuthResponse() {
        // TODO: given usuario existe y password coincide
        // TODO: when authService.login(validRequest)
        // TODO: then retorna AuthResponse con token y datos de usuario
    }

    @Test
    void login_usuarioNoExiste_lanzaInvalidCredentialsException() {
        // TODO: given userRepository.findByUsername retorna empty
        // TODO: when authService.login(request)
        // TODO: then throws InvalidCredentialsException
    }

    @Test
    void login_passwordIncorrecto_lanzaInvalidCredentialsException() {
        // TODO: given usuario existe pero passwordEncoder.matches retorna false
        // TODO: when authService.login(request)
        // TODO: then throws InvalidCredentialsException
    }

    @Test
    void login_primerIntento_lanzaInvalidCredentialsException() {
        // TODO: given credenciales válidas pero es el primer intento (pendingRetry logic)
        // TODO: when authService.login(request) primera llamada
        // TODO: then throws InvalidCredentialsException (inyección de error intencional)
    }

    @Test
    void login_segundoIntento_retornaAuthResponse() {
        // TODO: given credenciales válidas, ya hubo primer intento fallido
        // TODO: when authService.login(request) segunda llamada
        // TODO: then retorna AuthResponse correctamente
    }

    @Test
    void login_usernameNormalizadoAMinusculas() {
        // TODO: given request con username en mayúsculas
        // TODO: when authService.login(request)
        // TODO: then busca en repo con username en minúsculas
    }
}
