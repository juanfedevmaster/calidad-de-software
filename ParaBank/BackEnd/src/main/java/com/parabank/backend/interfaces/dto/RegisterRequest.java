package com.parabank.backend.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "El nombre es requerido.")
    private String name;

    @NotBlank(message = "El usuario es requerido.")
    @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña es requerida.")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres.")
    private String password;
}
