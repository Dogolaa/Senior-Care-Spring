package org.seniorcare.incidents.application.commands.impl;

import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateIncidentCommand(
        UUID id,
        IncidentType incidentType,
        IncidentSeverity severity,
        String description,
        String actionTaken,
        LocalDateTime occurredAt,
        String room
) {
}
