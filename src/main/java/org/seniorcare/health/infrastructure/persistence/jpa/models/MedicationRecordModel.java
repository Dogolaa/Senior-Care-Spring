package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private LocalDateTime administrationDate;

    @Column(nullable = false)
    private UUID administeredById;

    @Column(nullable = false)
    private String dose;

    @OneToMany(mappedBy = "medicationRecord", fetch = FetchType.EAGER)
    private List<MedicationRecordPhotoModel> photos = new ArrayList<>();

    public MedicationRecordModel() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getResidentId() { return residentId; }
    public void setResidentId(UUID residentId) { this.residentId = residentId; }

    public UUID getMedicationId() { return medicationId; }
    public void setMedicationId(UUID medicationId) { this.medicationId = medicationId; }

    public LocalDateTime getAdministrationDate() { return administrationDate; }
    public void setAdministrationDate(LocalDateTime administrationDate) { this.administrationDate = administrationDate; }

    public UUID getAdministeredById() { return administeredById; }
    public void setAdministeredById(UUID administeredById) { this.administeredById = administeredById; }

    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }

    public List<MedicationRecordPhotoModel> getPhotos() { return photos; }
    public void setPhotos(List<MedicationRecordPhotoModel> photos) { this.photos = photos; }
}
