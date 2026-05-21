package org.seniorcare.health.application.queries.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MedicationRecordResponse(
        UUID id,
        UUID residentId,
        UUID medicationId,
        String medicationCommercialName,
        LocalDateTime administrationDate,
        UUID administeredById,
        String dose,
        List<String> photoUrls
) {
}
