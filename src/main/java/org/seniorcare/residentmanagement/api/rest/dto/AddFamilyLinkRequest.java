package org.seniorcare.residentmanagement.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddFamilyLinkRequest(

        @NotNull(message = "Family member ID is required.")
        UUID familyMemberId,

        @NotBlank(message = "Relationship is required.")
        @Size(max = 100, message = "Relationship cannot exceed 100 characters.")
        String relationship,

        @NotNull(message = "Primary contact flag is required.")
        Boolean isPrimaryContact
) {
}