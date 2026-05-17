package org.seniorcare.health.application.queries.dto;

import java.time.LocalDate;
import java.util.UUID;

public record MedicationRecordResponse(
        UUID id,
        UUID residentId,
        UUID medicationId,
        String medicationCommercialName,
        LocalDate administrationDate,
        UUID administeredById,
        String dose
) {
}
