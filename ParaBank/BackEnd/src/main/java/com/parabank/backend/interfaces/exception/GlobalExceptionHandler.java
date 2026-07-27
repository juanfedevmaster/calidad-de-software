package com.parabank.backend.interfaces.exception;

import com.parabank.backend.domain.exception.InvalidCredentialsException;
import com.parabank.backend.domain.exception.InvalidCurrentPasswordException;
import com.parabank.backend.domain.exception.ResourceNotFoundException;
import com.parabank.backend.domain.exception.UserAlreadyExistsException;
import com.parabank.backend.interfaces.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Centralized exception handling: translates domain and validation
 * exceptions into consistent HTTP responses, keeping controllers free of
 * try/catch noise.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex,
                                                                     HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex,
                                                                      HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCurrentPassword(InvalidCurrentPasswordException ex,
                                                                          HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
                                                                    HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                              HttpServletRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return buildResponse(HttpStatus.BAD_REQUEST, "Error de validación.", request, details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado.", request, null);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message,
                                                            HttpServletRequest request, List<String> details) {
        ApiErrorResponse body = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .details(details)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
