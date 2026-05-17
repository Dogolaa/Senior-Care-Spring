package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "medication_records")
public class MedicationRecordModel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID residentId;

    @Column(nullable = false)
    private UUID medicationId;

    @Column(nullable = false)
    private LocalDate administrationDate;

    @Column(nullable = false)
    private UUID administeredById; // Nurse ID

    @Column(nullable = false)
    private String dose;

    public MedicationRecordModel() {
    }

    // Getters and Setters
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
