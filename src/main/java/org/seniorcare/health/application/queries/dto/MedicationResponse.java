package org.seniorcare.health.application.queries.dto;

import java.util.UUID;

public record MedicationResponse(
        UUID id,
        String commercialName,
        String activeIngredient,
        String pharmaceuticalForm,
        String concentration,
        String manufacturer,
        String registrationNumber,
        String therapeuticClass,
        boolean controlledSubstance
) {
}
