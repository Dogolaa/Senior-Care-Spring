package org.seniorcare.health.application.queries.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActivityRecordHistoryResponse(
        UUID id,
        String activityName,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        UUID conductedById,
        String notes,
        LocalDate recordedAt,
        List<String> photoUrls
) {
}
