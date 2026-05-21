package org.seniorcare.health.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationRecord {

    private UUID id;
    private UUID residentId;
    private UUID medicationId;
    private LocalDateTime administrationDate;
    private UUID administeredById;
    private String dose;
    private List<String> photoUrls = new ArrayList<>();

    public MedicationRecord() {
    }

    public MedicationRecord(UUID residentId, UUID medicationId, LocalDateTime administrationDate, UUID administeredById, String dose) {
        this.id = UUID.randomUUID();
        this.residentId = residentId;
        this.medicationId = medicationId;
        this.administrationDate = administrationDate;
        this.administeredById = administeredById;
        this.dose = dose;
    }

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

    public LocalDateTime getAdministrationDate() {
        return administrationDate;
    }

    public void setAdministrationDate(LocalDateTime administrationDate) {
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

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
