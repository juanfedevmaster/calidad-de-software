package com.parabank.backend.application.service;

import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.LoginRequest;

/**
 * Use case dedicated exclusively to authenticating an existing user and
 * issuing a JWT. Separated from {@link UserService} on purpose (SRP).
 */
public interface AuthService {

    AuthResponse login(LoginRequest request);
}
