package com.parabank.backend.domain.repository;

import com.parabank.backend.domain.model.User;

import java.util.Optional;

/**
 * Port that the application layer depends on. Infrastructure provides the
 * concrete adapter (JPA + PostgreSQL), so high-level modules never depend on
 * low-level persistence details (Dependency Inversion Principle) and can be
 * unit-tested with a fake/in-memory implementation.
 */
public interface UserRepository {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User save(User user);
}
