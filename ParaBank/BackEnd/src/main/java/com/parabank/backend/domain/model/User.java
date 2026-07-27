package com.parabank.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing a ParaBank user.
 * Intentionally free of JPA/Spring annotations so the application layer
 * never depends on persistence details (Dependency Inversion Principle).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String name;
    private String username;
    private String passwordHash;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
