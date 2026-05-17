package org.seniorcare.health.application.queries.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PrescriptionResponse(
        UUID id,
        UUID healthRecordId,
        UUID medicationId,
        String medicationCommercialName,
        String dosage,
        LocalDate startDate,
        LocalDate endDate
) {
}
