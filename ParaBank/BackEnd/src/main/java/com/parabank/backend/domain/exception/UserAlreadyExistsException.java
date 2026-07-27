package com.parabank.backend.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("El usuario '" + username + "' ya existe.");
    }
}
