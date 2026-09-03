package com.parabank.backend.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parabank.backend.application.service.UserService;
import com.parabank.backend.interfaces.dto.ChangePasswordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    // --- GET /api/users/me/balance ---

    @Test
    @WithMockUser(username = "testuser")
    void getBalance_usuarioAutenticado_retorna200ConSaldo() throws Exception {
        // TODO: given userService.getBalance("testuser") retorna BalanceResponse mockeado
        // TODO: when GET /api/users/me/balance con token válido
        // TODO: then status 200 y body con accountNumber y balance
    }

    @Test
    void getBalance_sinAutenticacion_retorna401Unauthorized() throws Exception {
        // TODO: when GET /api/users/me/balance sin Bearer token
        // TODO: then status 401
    }

    @Test
    @WithMockUser(username = "testuser")
    void getBalance_usuarioNoExiste_retorna404NotFound() throws Exception {
        // TODO: given userService.getBalance lanza ResourceNotFoundException
        // TODO: when GET /api/users/me/balance
        // TODO: then status 404
    }

    // --- PUT /api/users/me/password ---

    @Test
    @WithMockUser(username = "testuser")
    void changePassword_requestValida_retorna204NoContent() throws Exception {
        // TODO: given ChangePasswordRequest válido y userService.changePassword no lanza excepción
        // TODO: when PUT /api/users/me/password
        // TODO: then status 204
    }

    @Test
    void changePassword_sinAutenticacion_retorna401Unauthorized() throws Exception {
        // TODO: when PUT /api/users/me/password sin Bearer token
        // TODO: then status 401
    }

    @Test
    @WithMockUser(username = "testuser")
    void changePassword_passwordActualIncorrecto_retorna400BadRequest() throws Exception {
        // TODO: given userService.changePassword lanza InvalidCurrentPasswordException
        // TODO: when PUT /api/users/me/password
        // TODO: then status 400
    }

    @Test
    @WithMockUser(username = "testuser")
    void changePassword_requestSinCampos_retorna400BadRequest() throws Exception {
        // TODO: given ChangePasswordRequest vacío (Bean Validation falla)
        // TODO: when PUT /api/users/me/password
        // TODO: then status 400
    }
}
