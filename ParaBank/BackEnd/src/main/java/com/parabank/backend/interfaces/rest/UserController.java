package com.parabank.backend.interfaces.rest;

import com.parabank.backend.application.service.UserService;
import com.parabank.backend.interfaces.dto.BalanceResponse;
import com.parabank.backend.interfaces.dto.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Operaciones sobre el usuario autenticado")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/balance")
    @Operation(summary = "Consulta el saldo del usuario autenticado")
    public ResponseEntity<BalanceResponse> getBalance(Authentication authentication) {
        BalanceResponse response = userService.getBalance(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    @Operation(summary = "Cambia la contraseña del usuario autenticado")
    public ResponseEntity<Void> changePassword(Authentication authentication,
                                                @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(authentication.getName(), request);
        return ResponseEntity.noContent().build();
    }
}
