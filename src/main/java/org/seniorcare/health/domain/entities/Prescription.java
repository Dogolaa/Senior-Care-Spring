package org.seniorcare.health.domain.entities;

import org.seniorcare.shared.domain.Default;

import java.time.LocalDate;
import java.util.UUID;

public class Prescription {

    private final UUID id;
    private final UUID medicalRecordId;
    private final UUID medicationId;
    private final String dosage;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Prescription(UUID medicalRecordId, UUID medicationId, String dosage,
            LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.medicalRecordId = medicalRecordId;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Default
    public Prescription(UUID id, UUID medicalRecordId, UUID medicationId, String dosage,
            LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.medicalRecordId = medicalRecordId;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getMedicalRecordId() {
        return medicalRecordId;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public String getDosage() {
        return dosage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
