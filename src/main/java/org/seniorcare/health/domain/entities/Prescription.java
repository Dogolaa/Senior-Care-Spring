package org.seniorcare.health.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Prescription {

    private UUID id;
    private UUID medicalRecordId;
    private UUID medicationId;
    private String dosage;
    private LocalDate startDate;
    private LocalDate endDate;

    public Prescription() {
    }

    public Prescription(UUID medicalRecordId, UUID medicationId, String dosage, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.medicalRecordId = medicalRecordId;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(UUID medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
