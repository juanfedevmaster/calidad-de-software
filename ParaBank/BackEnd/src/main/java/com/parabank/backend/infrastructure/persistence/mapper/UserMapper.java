package com.parabank.backend.infrastructure.persistence.mapper;

import com.parabank.backend.domain.model.User;
import com.parabank.backend.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Translates between the JPA entity (infrastructure detail) and the
 * framework-agnostic domain model, keeping persistence concerns out of the
 * domain/application layers.
 */
@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .passwordHash(entity.getPasswordHash())
                .accountNumber(entity.getAccountNumber())
                .balance(entity.getBalance())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .username(domain.getUsername())
                .passwordHash(domain.getPasswordHash())
                .accountNumber(domain.getAccountNumber())
                .balance(domain.getBalance())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
