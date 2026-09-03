package com.parabank.backend.application.service.impl;

import com.parabank.backend.application.security.TokenProvider;
import com.parabank.backend.domain.exception.InvalidCurrentPasswordException;
import com.parabank.backend.domain.exception.ResourceNotFoundException;
import com.parabank.backend.domain.exception.UserAlreadyExistsException;
import com.parabank.backend.domain.model.User;
import com.parabank.backend.domain.repository.UserRepository;
import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.BalanceResponse;
import com.parabank.backend.interfaces.dto.ChangePasswordRequest;
import com.parabank.backend.interfaces.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private User existingUser;

    @BeforeEach
    void setUp() {
        // TODO: inicializar registerRequest, existingUser con datos de prueba
    }

    // --- getBalance ---

    @Test
    void getBalance_usuarioExiste_retornaBalanceResponse() {
        // Arrange

        User user = User.builder()
            .username("Katherine")
            .accountNumber("1111111111")
            .balance(new BigDecimal(50000))
            .build();
        
        when(userRepository.findByUsername("Katherine")).thenReturn(Optional.of(user));

        // Act

        BalanceResponse response = userService.getBalance("Katherine");

        // Assert
        assertEquals("1111111111", response.getAccountNumber());
        assertEquals(new BigDecimal(5000000), response.getBalance());
    }
}
