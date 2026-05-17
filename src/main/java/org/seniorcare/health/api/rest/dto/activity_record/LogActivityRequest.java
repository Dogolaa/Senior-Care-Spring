package org.seniorcare.health.api.rest.dto.activity_record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogActivityRequest {

    @NotBlank
    private String activityName;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @NotNull
    private UUID conductedById;

    private String notes;

    public LogActivityRequest() {
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public UUID getConductedById() {
        return conductedById;
    }

    public void setConductedById(UUID conductedById) {
        this.conductedById = conductedById;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
