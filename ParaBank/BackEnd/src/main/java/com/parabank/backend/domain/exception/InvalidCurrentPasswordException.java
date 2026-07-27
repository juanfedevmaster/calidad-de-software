package com.parabank.backend.domain.exception;

public class InvalidCurrentPasswordException extends RuntimeException {

    public InvalidCurrentPasswordException() {
        super("La contraseña actual no es correcta.");
    }
}
