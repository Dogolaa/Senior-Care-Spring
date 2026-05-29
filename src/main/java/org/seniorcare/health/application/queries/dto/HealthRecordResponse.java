package org.seniorcare.health.application.queries.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HealthRecordResponse(
        UUID id,
        UUID residentId,
        UUID updatedById,
        Float height,
        Float weight,
        String bloodPressure,
        Integer heartRate,
        Float temperature,
        Float saturation,
        Float imc,
        LocalDate lastUpdated,
        List<HealthRecordHistoryResponse> history,
        List<String> conditions
) {
}
