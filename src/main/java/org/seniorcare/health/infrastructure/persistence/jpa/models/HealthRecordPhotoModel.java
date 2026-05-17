package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "health_record_photos")
public class HealthRecordPhotoModel {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_history_id", nullable = false)
    private HealthRecordHistoryModel healthRecordHistory;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    public HealthRecordPhotoModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HealthRecordHistoryModel getHealthRecordHistory() {
        return healthRecordHistory;
    }

    public void setHealthRecordHistory(HealthRecordHistoryModel healthRecordHistory) {
        this.healthRecordHistory = healthRecordHistory;
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
