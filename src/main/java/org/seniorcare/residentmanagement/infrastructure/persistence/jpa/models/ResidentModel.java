package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLRestriction;
import org.seniorcare.shared.infrastructure.persistence.Auditable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "residents")
@SQLRestriction("deleted_at IS NULL")
public class ResidentModel extends Auditable {

    @Id
    private UUID id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "primary_responsible_id", nullable = false)
    private UUID responsibleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public ResidentModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(UUID responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResidentModel that)) return false;
        return isActive() == that.isActive() && Objects.equals(getId(), that.getId()) && Objects.equals(getCpf(), that.getCpf()) && Objects.equals(getRg(), that.getRg()) && Objects.equals(getDateOfBirth(), that.getDateOfBirth()) && Objects.equals(getResponsibleId(), that.getResponsibleId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getBloodType(), that.getBloodType()) && Objects.equals(getAdmissionDate(), that.getAdmissionDate()) && Objects.equals(getRoom(), that.getRoom()) && Objects.equals(getDeletedAt(), that.getDeletedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf(), getRg(), getDateOfBirth(), getResponsibleId(), getName(), getGender(), getBloodType(), isActive(), getAdmissionDate(), getRoom(), getDeletedAt());
    }
}
