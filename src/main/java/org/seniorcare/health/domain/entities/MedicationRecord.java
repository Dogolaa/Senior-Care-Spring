package org.seniorcare.health.domain.entities;

import org.seniorcare.shared.domain.Default;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicationRecord {

    private final UUID id;
    private final UUID residentId;
    private final UUID medicationId;
    private final LocalDateTime administrationDate;
    private final UUID administeredById;
    private final String dose;
    private final List<String> photoUrls;

    public MedicationRecord(UUID residentId, UUID medicationId, LocalDateTime administrationDate,
            UUID administeredById, String dose) {
        this.id = UUID.randomUUID();
        this.residentId = residentId;
        this.medicationId = medicationId;
        this.administrationDate = administrationDate;
        this.administeredById = administeredById;
        this.dose = dose;
        this.photoUrls = new ArrayList<>();
    }

    @Default
    public MedicationRecord(UUID id, UUID residentId, UUID medicationId, LocalDateTime administrationDate,
            UUID administeredById, String dose, List<String> photoUrls) {
        this.id = id;
        this.residentId = residentId;
        this.medicationId = medicationId;
        this.administrationDate = administrationDate;
        this.administeredById = administeredById;
        this.dose = dose;
        this.photoUrls = photoUrls != null ? photoUrls : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getResidentId() {
        return residentId;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public LocalDateTime getAdministrationDate() {
        return administrationDate;
    }

    public UUID getAdministeredById() {
        return administeredById;
    }

    public String getDose() {
        return dose;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}
