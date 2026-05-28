package org.seniorcare.health.domain.aggregates;

import org.seniorcare.health.domain.entities.ActivityRecordHistory;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityRecord {

    private final UUID id;
    private UUID residentId;
    private UUID conductedById;
    private LocalDate lastActivityDate;
    private List<ActivityRecordHistory> history;

    public ActivityRecord(UUID id, UUID residentId, UUID conductedById,
            LocalDate lastActivityDate, List<ActivityRecordHistory> history) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        this.id = id;
        this.residentId = residentId;
        this.conductedById = conductedById;
        this.lastActivityDate = lastActivityDate;
        this.history = history != null ? history : new ArrayList<>();
    }

    public static ActivityRecord create(UUID residentId, UUID conductedById) {
        if (residentId == null) {
            throw new BadRequestException("Resident ID is required to create an activity record.");
        }
        if (conductedById == null) {
            throw new BadRequestException("Staff ID (conductedById) is required to create an activity record.");
        }
        return new ActivityRecord(UUID.randomUUID(), residentId, conductedById, null, new ArrayList<>());
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

    public UUID getResidentId() {
        return residentId;
    }

    public UUID getConductedById() {
        return conductedById;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public List<ActivityRecordHistory> getHistory() {
        return history;
    }
}
