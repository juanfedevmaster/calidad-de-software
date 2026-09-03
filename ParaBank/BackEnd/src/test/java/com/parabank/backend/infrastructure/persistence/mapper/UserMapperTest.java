package com.parabank.backend.infrastructure.persistence.mapper;

import com.parabank.backend.domain.model.User;
import com.parabank.backend.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    // --- toDomain ---

    @Test
    void toDomain_entityValida_retornaUserConTodosLosCampos() {
        // TODO: given UserEntity con todos los campos completos
        // TODO: when userMapper.toDomain(entity)
        // TODO: then User resultante tiene id, name, username, passwordHash, accountNumber, balance, createdAt correctos
    }

    @Test
    void toDomain_entityNula_retornaNull() {
        // TODO: when userMapper.toDomain(null)
        // TODO: then retorna null
    }

    // --- toEntity ---

    @Test
    void toEntity_userValido_retornaEntityConTodosLosCampos() {
        // TODO: given User con todos los campos completos
        // TODO: when userMapper.toEntity(user)
        // TODO: then UserEntity resultante tiene id, name, username, passwordHash, accountNumber, balance, createdAt correctos
    }

    @Test
    void toEntity_userNulo_retornaNull() {
        // TODO: when userMapper.toEntity(null)
        // TODO: then retorna null
    }

    @Test
    void toDomain_toEntity_roundTrip_preservaTodosLosCampos() {
        // TODO: given User original con campos conocidos
        // TODO: when toEntity luego toDomain
        // TODO: then User resultante es equivalente al original
    }
}
