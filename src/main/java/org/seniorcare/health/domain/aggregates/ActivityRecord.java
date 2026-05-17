package org.seniorcare.health.domain.aggregates;

import org.seniorcare.health.domain.entities.ActivityRecordHistory;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityRecord {

    private UUID id;
    private UUID residentId;
    private UUID conductedById;
    private LocalDate lastActivityDate;
    private List<ActivityRecordHistory> history = new ArrayList<>();

    public ActivityRecord() {
    }

    public static ActivityRecord create(UUID residentId, UUID conductedById) {
        if (residentId == null) {
            throw new BadRequestException("Resident ID is required to create an activity record.");
        }
        if (conductedById == null) {
            throw new BadRequestException("Staff ID (conductedById) is required to create an activity record.");
        }
        ActivityRecord record = new ActivityRecord();
        record.id = UUID.randomUUID();
        record.residentId = residentId;
        record.conductedById = conductedById;
        record.lastActivityDate = null;
        record.history = new ArrayList<>();
        return record;
    }

    public UUID logActivity(String activityName, String description, LocalDateTime startDateTime,
                             LocalDateTime endDateTime, UUID conductedById, String notes) {
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new BadRequestException("Activity name cannot be empty.");
        }
        if (startDateTime == null) {
            throw new BadRequestException("Activity start date/time is required.");
        }
        if (endDateTime != null && endDateTime.isBefore(startDateTime)) {
            throw new BadRequestException("Activity end time cannot be before start time.");
        }

        this.conductedById = conductedById;
        this.lastActivityDate = LocalDate.now();

        ActivityRecordHistory entry = new ActivityRecordHistory(
                this.id, activityName, description, startDateTime, endDateTime, conductedById, notes
        );
        this.history.add(entry);
        return entry.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getResidentId() {
        return residentId;
    }

    public void setResidentId(UUID residentId) {
        this.residentId = residentId;
    }

    public UUID getConductedById() {
        return conductedById;
    }

    public void setConductedById(UUID conductedById) {
        this.conductedById = conductedById;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public List<ActivityRecordHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ActivityRecordHistory> history) {
        this.history = history;
    }
}
