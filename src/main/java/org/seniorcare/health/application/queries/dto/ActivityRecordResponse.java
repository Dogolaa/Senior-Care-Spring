package org.seniorcare.health.application.queries.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ActivityRecordResponse(
        UUID id,
        UUID residentId,
        UUID conductedById,
        LocalDate lastActivityDate,
        List<ActivityRecordHistoryResponse> history
) {
}
