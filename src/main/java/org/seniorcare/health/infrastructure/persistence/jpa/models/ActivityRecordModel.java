package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "activity_records")
public class ActivityRecordModel {

    @Id
    private UUID id;

    @Column(name = "resident_id", nullable = false)
    private UUID residentId;

    @Column(name = "conducted_by_id", nullable = false)
    private UUID conductedById;

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;

    @OneToMany(mappedBy = "activityRecord", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ActivityRecordHistoryModel> history = new ArrayList<>();

    public ActivityRecordModel() {
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

    public List<ActivityRecordHistoryModel> getHistory() {
        return history;
    }

    public void setHistory(List<ActivityRecordHistoryModel> history) {
        this.history = history;
    }
}
