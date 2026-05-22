package org.seniorcare.incidents.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.seniorcare.incidents.domain.vo.IncidentSeverity;
import org.seniorcare.incidents.domain.vo.IncidentType;

import java.time.LocalDateTime;

public class UpdateIncidentRequest {

    @NotNull
    private IncidentType incidentType;

    @NotNull
    private IncidentSeverity severity;

    @NotBlank
    private String description;

    private String actionTaken;

    @NotNull
    private LocalDateTime occurredAt;

    private String room;

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
}
