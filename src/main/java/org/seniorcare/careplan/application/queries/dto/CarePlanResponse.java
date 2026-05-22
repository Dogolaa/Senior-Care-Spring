package org.seniorcare.careplan.application.queries.dto;

import org.seniorcare.careplan.domain.vo.CarePlanStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CarePlanResponse(
        UUID id,
        UUID residentId,
        UUID responsibleId,
        String title,
        String description,
        List<String> goals,
        List<String> interventions,
        LocalDate startDate,
        LocalDate endDate,
        CarePlanStatus status,
        Instant createdAt,
        Instant updatedAt
) {}
