package org.seniorcare.health.domain.entities;

import org.seniorcare.shared.domain.Default;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityRecordHistory {

    private final UUID id;
    private final UUID activityRecordId;
    private final String activityName;
    private final String description;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final UUID conductedById;
    private final String notes;
    private final LocalDate recordedAt;
    private final List<String> photoUrls;

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

    @Default
    public ActivityRecordHistory(UUID id, UUID activityRecordId, String activityName, String description,
                                  LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  UUID conductedById, String notes, LocalDate recordedAt, List<String> photoUrls) {
        this.id = id;
        this.activityRecordId = activityRecordId;
        this.activityName = activityName;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.conductedById = conductedById;
        this.notes = notes;
        this.recordedAt = recordedAt;
        this.photoUrls = photoUrls != null ? photoUrls : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getActivityRecordId() {
        return activityRecordId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public UUID getConductedById() {
        return conductedById;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getRecordedAt() {
        return recordedAt;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}
