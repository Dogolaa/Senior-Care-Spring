package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "activity_record_photos")
public class ActivityRecordPhotoModel {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_record_history_id", nullable = false)
    private ActivityRecordHistoryModel activityRecordHistory;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    public ActivityRecordPhotoModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ActivityRecordHistoryModel getActivityRecordHistory() {
        return activityRecordHistory;
    }

    public void setActivityRecordHistory(ActivityRecordHistoryModel activityRecordHistory) {
        this.activityRecordHistory = activityRecordHistory;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
