package org.seniorcare.health.application.queries.dto;

import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HealthRecordHistoryResponse(
        UUID id,
        Float height,
        Float weight,
        String bloodPressure,
        Integer heartRate,
        Float temperature,
        Float saturation,
        Float imc,
        LocalDate updateDate,
        VitalSignsSource source,
        List<String> photoUrls
) {
}
