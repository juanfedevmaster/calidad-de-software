package com.parabank.backend.application.service.impl;

import com.parabank.backend.application.security.TokenProvider;
import com.parabank.backend.application.service.AuthService;
import com.parabank.backend.domain.exception.InvalidCredentialsException;
import com.parabank.backend.domain.model.User;
import com.parabank.backend.domain.repository.UserRepository;
import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link AuthService}. Its only job is verifying
 * credentials and issuing a token - it never touches registration or
 * password-change logic (SRP).
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public AuthResponse login(LoginRequest request) {
        String normalizedUsername = request.getUsername().trim().toLowerCase();

        User user = userRepository.findByUsername(normalizedUsername)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenProvider.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .name(user.getName())
                .accountNumber(user.getAccountNumber())
                .balance(user.getBalance())
                .build();
    }
}
