package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "medication_record_photos")
public class MedicationRecordPhotoModel {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_record_id", nullable = false)
    private MedicationRecordModel medicationRecord;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    public MedicationRecordPhotoModel() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public MedicationRecordModel getMedicationRecord() { return medicationRecord; }
    public void setMedicationRecord(MedicationRecordModel medicationRecord) { this.medicationRecord = medicationRecord; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }
}
