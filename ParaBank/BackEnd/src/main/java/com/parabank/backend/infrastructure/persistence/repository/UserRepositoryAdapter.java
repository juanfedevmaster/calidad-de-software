package com.parabank.backend.infrastructure.persistence.repository;

import com.parabank.backend.domain.model.User;
import com.parabank.backend.domain.repository.UserRepository;
import com.parabank.backend.infrastructure.persistence.entity.UserEntity;
import com.parabank.backend.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adapter that implements the domain {@link UserRepository} port using
 * Spring Data JPA. This is the only class that knows persistence is JPA +
 * PostgreSQL; every other layer depends solely on the port.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
