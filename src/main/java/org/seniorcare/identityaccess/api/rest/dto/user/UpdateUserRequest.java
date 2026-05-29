package org.seniorcare.identityaccess.api.rest.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateUserRequest(
        @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
        String name,

        @Email(message = "Formato de e-mail inválido")
        String email,

        @Size(min = 10, max = 20, message = "Telefone deve ter entre 10 e 20 caracteres")
        String phone,

        UUID addressId,

        UUID roleId
) {}
