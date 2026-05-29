package org.seniorcare.identityaccess.api.rest.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @NotBlank(message = "CEP é obrigatório")
        @Size(min = 8, max = 10, message = "CEP inválido")
        String cep,

        @NotBlank(message = "País é obrigatório")
        String country,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres (UF)")
        String state,

        @NotBlank(message = "Cidade é obrigatória")
        String city,

        @NotBlank(message = "Bairro é obrigatório")
        String district,

        @NotBlank(message = "Rua é obrigatória")
        String street,

        Integer number,

        String complement
) {}
