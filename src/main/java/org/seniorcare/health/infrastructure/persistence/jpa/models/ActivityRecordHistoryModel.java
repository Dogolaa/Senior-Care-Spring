package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "activity_record_histories")
public class ActivityRecordHistoryModel {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_record_id", nullable = false)
    private ActivityRecordModel activityRecord;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "conducted_by_id", nullable = false)
    private UUID conductedById;

    @Column(name = "notes")
    private String notes;

    @Column(name = "recorded_at", nullable = false)
    private LocalDate recordedAt;

    @OneToMany(mappedBy = "activityRecordHistory", fetch = FetchType.EAGER)
    private List<ActivityRecordPhotoModel> photos = new ArrayList<>();

    public ActivityRecordHistoryModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ActivityRecordModel getActivityRecord() {
        return activityRecord;
    }

    public void setActivityRecord(ActivityRecordModel activityRecord) {
        this.activityRecord = activityRecord;
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

    public List<ActivityRecordPhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ActivityRecordPhotoModel> photos) {
        this.photos = photos;
    }
}
