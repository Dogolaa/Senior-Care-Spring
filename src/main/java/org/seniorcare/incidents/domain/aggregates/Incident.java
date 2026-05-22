package org.seniorcare.incidents.domain.aggregates;

import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;
import org.seniorcare.shared.domain.BaseAggregateRoot;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class Incident extends BaseAggregateRoot {

    private final UUID id;
    private UUID residentId;
    private UUID reportedById;
    private IncidentType incidentType;
    private IncidentSeverity severity;
    private String description;
    private String actionTaken;
    private LocalDateTime occurredAt;
    private String room;
    private Instant createdAt;
    private Instant updatedAt;

    private Incident(UUID residentId, UUID reportedById, IncidentType incidentType,
            IncidentSeverity severity, String description, String actionTaken,
            LocalDateTime occurredAt, String room) {

        if (residentId == null) throw new BadRequestException("ResidentId cannot be null.");
        if (reportedById == null) throw new BadRequestException("ReportedById cannot be null.");
        if (description == null || description.isBlank()) throw new BadRequestException("Incident description cannot be empty.");
        if (occurredAt == null) throw new BadRequestException("OccurredAt cannot be null.");

        this.id = UUID.randomUUID();
        this.residentId = residentId;
        this.reportedById = reportedById;
        this.incidentType = incidentType != null ? incidentType : IncidentType.OTHER;
        this.severity = severity != null ? severity : IncidentSeverity.MEDIUM;
        this.description = description.trim();
        this.actionTaken = actionTaken != null ? actionTaken.trim() : null;
        this.occurredAt = occurredAt;
        this.room = room;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Incident(UUID id, UUID residentId, UUID reportedById, IncidentType incidentType,
            IncidentSeverity severity, String description, String actionTaken,
            LocalDateTime occurredAt, String room, Instant createdAt, Instant updatedAt) {

        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        this.id = id;
        this.residentId = residentId;
        this.reportedById = reportedById;
        this.incidentType = incidentType;
        this.severity = severity;
        this.description = description;
        this.actionTaken = actionTaken;
        this.occurredAt = occurredAt;
        this.room = room;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Incident report(UUID residentId, UUID reportedById, IncidentType incidentType,
            IncidentSeverity severity, String description, String actionTaken,
            LocalDateTime occurredAt, String room) {
        return new Incident(residentId, reportedById, incidentType, severity,
                description, actionTaken, occurredAt, room);
    }

    public void update(IncidentType incidentType, IncidentSeverity severity, String description,
            String actionTaken, LocalDateTime occurredAt, String room) {
        if (description == null || description.isBlank()) throw new BadRequestException("Incident description cannot be empty.");
        if (occurredAt == null) throw new BadRequestException("OccurredAt cannot be null.");
        this.incidentType = incidentType != null ? incidentType : this.incidentType;
        this.severity = severity != null ? severity : this.severity;
        this.description = description.trim();
        this.actionTaken = actionTaken != null ? actionTaken.trim() : null;
        this.occurredAt = occurredAt;
        this.room = room;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getResidentId() { return residentId; }
    public UUID getReportedById() { return reportedById; }
    public IncidentType getIncidentType() { return incidentType; }
    public IncidentSeverity getSeverity() { return severity; }
    public String getDescription() { return description; }
    public String getActionTaken() { return actionTaken; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getRoom() { return room; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
