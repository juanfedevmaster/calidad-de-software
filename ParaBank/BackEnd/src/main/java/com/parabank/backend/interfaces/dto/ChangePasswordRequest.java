package com.parabank.backend.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "La contraseña actual es requerida.")
    private String currentPassword;

    @NotBlank(message = "La nueva contraseña es requerida.")
    @Size(min = 4, message = "La nueva contraseña debe tener al menos 4 caracteres.")
    private String newPassword;
}
