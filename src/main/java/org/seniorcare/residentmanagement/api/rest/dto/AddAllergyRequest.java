package org.seniorcare.residentmanagement.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddAllergyRequest(
        @NotBlank(message = "Allergy description is required")
        @Size(max = 255, message = "Allergy description must be at most 255 characters")
        String allergyDescription
) {
}
