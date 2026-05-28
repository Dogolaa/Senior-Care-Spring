package org.seniorcare.health.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class HealthRecordPhoto {

    private final UUID id;
    private final String photoUrl;
    private final Instant uploadedAt;

    public HealthRecordPhoto(String photoUrl) {
        this.id = UUID.randomUUID();
        this.photoUrl = photoUrl;
        this.uploadedAt = Instant.now();
    }

    public HealthRecordPhoto(UUID id, String photoUrl, Instant uploadedAt) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.uploadedAt = uploadedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }
}
