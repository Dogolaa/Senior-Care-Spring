package org.seniorcare.health.api.rest.dto.medication_record;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class LogMedicationAdministrationRequest {

    @NotNull
    private UUID residentId;

    @NotNull
    private UUID medicationId;

    @NotNull
    private LocalDate administrationDate;

    @NotNull
    private UUID administeredById;

    @NotNull
    private String dose;

    public UUID getResidentId() {
        return residentId;
    }

    public void setResidentId(UUID residentId) {
        this.residentId = residentId;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }

    public LocalDate getAdministrationDate() {
        return administrationDate;
    }

    public void setAdministrationDate(LocalDate administrationDate) {
        this.administrationDate = administrationDate;
    }

    public UUID getAdministeredById() {
        return administeredById;
    }

    public void setAdministeredById(UUID administeredById) {
        this.administeredById = administeredById;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
