package org.seniorcare.identityaccess.api.rest.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateUserRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
        String name,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        @Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
        String phone,

        UUID addressId,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String password
) {}
