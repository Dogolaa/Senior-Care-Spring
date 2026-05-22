package org.seniorcare.incidents.application.queries.dto;

import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record IncidentResponse(
        UUID id,
        UUID residentId,
        UUID reportedById,
        IncidentType incidentType,
        IncidentSeverity severity,
        String description,
        String actionTaken,
        LocalDateTime occurredAt,
        String room,
        Instant createdAt,
        Instant updatedAt
) {
}
