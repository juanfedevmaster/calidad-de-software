package com.parabank.backend.application.service.impl;

import com.parabank.backend.application.security.TokenProvider;
import com.parabank.backend.application.service.UserService;
import com.parabank.backend.domain.exception.InvalidCurrentPasswordException;
import com.parabank.backend.domain.exception.ResourceNotFoundException;
import com.parabank.backend.domain.exception.UserAlreadyExistsException;
import com.parabank.backend.domain.model.User;
import com.parabank.backend.domain.repository.UserRepository;
import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.BalanceResponse;
import com.parabank.backend.interfaces.dto.ChangePasswordRequest;
import com.parabank.backend.interfaces.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Default implementation of {@link UserService}. Depends only on
 * abstractions (UserRepository, PasswordEncoder, TokenProvider), following
 * the Dependency Inversion Principle - none of them are constructed here,
 * they are injected by Spring (constructor injection via Lombok).
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("5000.00");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedUsername = request.getUsername().trim().toLowerCase();

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new UserAlreadyExistsException(normalizedUsername);
        }

        User user = User.builder()
                .name(request.getName().trim())
                .username(normalizedUsername)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .accountNumber(generateAccountNumber())
                .balance(INITIAL_BALANCE)
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        String token = tokenProvider.generateToken(saved.getUsername());

        return AuthResponse.builder()
                .token(token)
                .username(saved.getUsername())
                .name(saved.getName())
                .accountNumber(saved.getAccountNumber())
                .balance(saved.getBalance())
                .build();
    }

    @Override
    public BalanceResponse getBalance(String username) {
        User user = findUserOrThrow(username);
        return BalanceResponse.builder()
                .accountNumber(user.getAccountNumber())
                .balance(user.getBalance())
                .build();
    }

    @Override
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        User user = findUserOrThrow(username);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new InvalidCurrentPasswordException();
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private User findUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
    }

    private String generateAccountNumber() {
        long candidate = ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L);
        return String.valueOf(candidate);
    }
}
