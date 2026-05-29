package org.seniorcare.health.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddConditionRequest(
        @NotBlank(message = "A descrição da condição é obrigatória")
        @Size(max = 255, message = "Descrição não pode ultrapassar 255 caracteres")
        String conditionDescription
) {}
