package com.parabank.backend.application.service;

import com.parabank.backend.interfaces.dto.AuthResponse;
import com.parabank.backend.interfaces.dto.BalanceResponse;
import com.parabank.backend.interfaces.dto.ChangePasswordRequest;
import com.parabank.backend.interfaces.dto.RegisterRequest;

/**
 * Use cases related to a ParaBank user account: registration, balance
 * lookup and password management. Kept separate from {@link AuthService}
 * to respect the Single Responsibility Principle.
 */
public interface UserService {

    AuthResponse register(RegisterRequest request);

    BalanceResponse getBalance(String username);

    void changePassword(String username, ChangePasswordRequest request);
}
