package org.seniorcare.health.api.rest.dto.prescription;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class CreatePrescriptionRequest {

    @NotNull
    private UUID medicalRecordId;

    @NotNull
    private UUID medicationId;

    @NotNull
    private String dosage;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

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
