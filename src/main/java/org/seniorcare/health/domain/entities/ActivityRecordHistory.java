package org.seniorcare.health.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityRecordHistory {

    private UUID id;
    private UUID activityRecordId;
    private String activityName;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UUID conductedById;
    private String notes;
    private LocalDate recordedAt;
    private List<String> photoUrls = new ArrayList<>();

    public ActivityRecordHistory() {
    }

    public ActivityRecordHistory(UUID activityRecordId, String activityName, String description,
                                  LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  UUID conductedById, String notes) {
        this.id = UUID.randomUUID();
        this.activityRecordId = activityRecordId;
        this.activityName = activityName;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.conductedById = conductedById;
        this.notes = notes;
        this.recordedAt = LocalDate.now();
        this.photoUrls = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getActivityRecordId() {
        return activityRecordId;
    }

    public void setActivityRecordId(UUID activityRecordId) {
        this.activityRecordId = activityRecordId;
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

    public LocalDate getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDate recordedAt) {
        this.recordedAt = recordedAt;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
