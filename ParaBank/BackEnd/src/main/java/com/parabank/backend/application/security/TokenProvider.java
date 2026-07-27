package com.parabank.backend.application.security;

/**
 * Abstraction over token generation/validation. The application layer
 * depends on this interface, not on the JWT library directly (Dependency
 * Inversion Principle), so the token technology could be swapped without
 * touching any use case.
 */
public interface TokenProvider {

    String generateToken(String username);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}
