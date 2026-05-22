package org.seniorcare.incidents.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "incidents")
public class IncidentModel {

    @Id
    private UUID id;

    @Column(name = "resident_id", nullable = false)
    private UUID residentId;

    @Column(name = "reported_by_id", nullable = false)
    private UUID reportedById;

    @Enumerated(EnumType.STRING)
    @Column(name = "incident_type", nullable = false, length = 50)
    private IncidentType incidentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 20)
    private IncidentSeverity severity;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "action_taken", columnDefinition = "TEXT")
    private String actionTaken;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "room", length = 50)
    private String room;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public IncidentModel() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getResidentId() { return residentId; }
    public void setResidentId(UUID residentId) { this.residentId = residentId; }

    public UUID getReportedById() { return reportedById; }
    public void setReportedById(UUID reportedById) { this.reportedById = reportedById; }

    public IncidentType getIncidentType() { return incidentType; }
    public void setIncidentType(IncidentType incidentType) { this.incidentType = incidentType; }

    public IncidentSeverity getSeverity() { return severity; }
    public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getActionTaken() { return actionTaken; }
    public void setActionTaken(String actionTaken) { this.actionTaken = actionTaken; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
