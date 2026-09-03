package com.parabank.backend.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parabank.backend.application.service.AuthService;
import com.parabank.backend.application.service.UserService;
import com.parabank.backend.interfaces.dto.LoginRequest;
import com.parabank.backend.interfaces.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Test
    void register_requestValida_retorna201Created() throws Exception {
        // TODO: given userService.register retorna AuthResponse mockeado
        // TODO: when POST /api/auth/register con RegisterRequest válido
        // TODO: then status 201 y body con token
    }

    @Test
    void register_requestSinUsername_retorna400BadRequest() throws Exception {
        // TODO: given RegisterRequest sin username (validación Bean Validation)
        // TODO: when POST /api/auth/register
        // TODO: then status 400
    }

    @Test
    void register_requestSinPassword_retorna400BadRequest() throws Exception {
        // TODO: given RegisterRequest sin password
        // TODO: when POST /api/auth/register
        // TODO: then status 400
    }

    @Test
    void login_credencialesValidas_retorna200Ok() throws Exception {
        // TODO: given authService.login retorna AuthResponse mockeado
        // TODO: when POST /api/auth/login con LoginRequest válido
        // TODO: then status 200 y body con token
    }

    @Test
    void login_credencialesInvalidas_retorna401Unauthorized() throws Exception {
        // TODO: given authService.login lanza InvalidCredentialsException
        // TODO: when POST /api/auth/login
        // TODO: then status 401
    }

    @Test
    void login_requestSinCampos_retorna400BadRequest() throws Exception {
        // TODO: given LoginRequest vacío
        // TODO: when POST /api/auth/login
        // TODO: then status 400
    }
}
